package june.seven.ark.fourpointzero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class StoriesActivity extends AppCompatActivity
{
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        try
        {
            final JSONObject jsonObject = new JSONObject();
            try
            {
                if(!getIntent().getExtras().getString("place_id").equals(null))
                    jsonObject.put("placeid", "ChIJpQvTG0uxbTkRDLLMHlNdDoY");

                else
                    jsonObject.put("placeid", "ChIJ-w3Sy1qwbTkRK34UR2ffIWI");

            }
            catch (Exception e)
            {
                jsonObject.put("placeid", "ChIJ-w3Sy1qwbTkRK34UR2ffIWI");
            }
            StringEntity entity = null;

            entity = new StringEntity(jsonObject.toString());


            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(), "http://192.168.137.248:3000/apis/getplacestories", entity, "application/json", new AsyncHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                {
                    try
                    {
                        if (!new String(responseBody, "UTF-8").contains("failed"))
                        {
                            Log.d("data", new String(responseBody, "UTF-8"));
                            JSONObject jsonObject1 = new JSONObject(new String(responseBody, "UTF-8"));

                            jsonObject1=jsonObject1.getJSONObject("data");
                            ((Button)findViewById(R.id.name_place)).setText(jsonObject1.getString("placename"));
                            jsonArray=jsonObject1.getJSONArray("reviews");
                            Log.d("jsonArray",jsonArray.toString());

                            ListView listView=findViewById(R.id.story_list);
                            StoryListAdapter adapter=new StoryListAdapter(getApplicationContext(),jsonArray);
                            listView.setAdapter(adapter);

                        }

                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                {

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

