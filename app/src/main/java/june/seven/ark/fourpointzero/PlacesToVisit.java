package june.seven.ark.fourpointzero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class PlacesToVisit extends AppCompatActivity
{
    @BindView(R.id.name_place)Button b;
    @BindView(R.id.review_list)ListView places;
    @BindView(R.id.write_review)Button toggle;

    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        b.setVisibility(View.GONE);
        toggle.setText("Show all places");

        show_selected_places();
        toggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(toggle.getText().equals("Show all places"))
                {
                    show_all_places();
                    toggle.setText("Show selected places");
                }
                else
                {
                    show_selected_places();
                    toggle.setText("Show all places");
                }
            }
        });
    }

    public void show_selected_places()
    {

        JSONObject jsonObject=new JSONObject();
        try
        {
            jsonObject.put("fbid",getIntent().getExtras().getString("fid"));
            StringEntity entity = null;

            entity = new StringEntity(jsonObject.toString());


            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(), "http://192.168.137.248:3000/apis/getinterestbasedplaces", entity, "application/json", new AsyncHttpResponseHandler()
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
                            jsonArray=jsonObject1.getJSONArray("places");
                            Log.d("jsonArray",jsonArray.toString());

                            ReviewListAdapter adapter=new ReviewListAdapter(getApplicationContext(),jsonArray);
                            places.setAdapter(adapter);

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
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void show_all_places()
    {

        JSONObject jsonObject=new JSONObject();
        try
        {
            jsonObject.put("fbid",getIntent().getExtras().getString("fid"));
            StringEntity entity = null;

            entity = new StringEntity(jsonObject.toString());


            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(getApplicationContext(), "http://192.168.137.248:3000/apis/getallplaces", entity, "application/json", new AsyncHttpResponseHandler()
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
                            jsonArray=jsonObject1.getJSONArray("places");
                            Log.d("jsonArray",jsonArray.toString());

                            places.invalidateViews();
                            ReviewListAdapter adapter=new ReviewListAdapter(getApplicationContext(),jsonArray);
                            places.setAdapter(adapter);

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
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
