package june.seven.ark.fourpointzero;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileManager;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity
{

    CallbackManager callbackManager;
    LoginButton loginButton;

    String Name,DOB,email,gender,fid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        String EMAIL = "email";

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker profileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.d("GETACCESSTOKEN",loginResult.getAccessToken().toString());
                Log.d("GEtUSERId",loginResult.getAccessToken().getUserId());
                fid=loginResult.getAccessToken().getUserId();
                if(Profile.getCurrentProfile() == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            profileTracker.stopTracking();
                            Name=currentProfile.getName();

                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                }


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                final JSONObject jsonObject = response.getJSONObject();
                                try{
                                    email = jsonObject.getString("email");
                                    gender = jsonObject.getString("gender");
                                    DOB = jsonObject.getString("birthday");
                                    DOB=DOB.replaceAll("/","-");
                                    gender=gender.substring(0,1);

                                    jsonObject.put("email",email);
                                    jsonObject.put("gender",gender);
                                    jsonObject.put("dob",DOB);
                                    jsonObject.put("name",Name);
                                    jsonObject.put("fbid",fid);

                                    StringEntity entity = new StringEntity(jsonObject.toString());
                                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                                    AsyncHttpClient client=new AsyncHttpClient();
                                    client.post(getApplicationContext(), "http://192.168.137.248:3000/apis/submitProfile", entity, "application/json", new AsyncHttpResponseHandler()
                                    {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                                        {
                                            try
                                            {
                                                if(!new String(responseBody, "UTF-8").contains("failed"))
                                                {
                                                    Log.d("data", new String(responseBody, "UTF-8"));
                                                }
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                intent.putExtra("name",Name);
                                                intent.putExtra("email",email);
                                                intent.putExtra("uri",Profile.getCurrentProfile().getProfilePictureUri(100,100));
                                                Log.d("URI",String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(100,100)));
                                                startActivity(intent);
                                            } catch (UnsupportedEncodingException e)
                                            {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                                        {

                                        }
                                    });

                                    Log.d("TAG",DOB+gender+email);
                                } catch (JSONException e){
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,first_name,last_name,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);


    }

    public void getProfileInfo()
    {

    }
}
