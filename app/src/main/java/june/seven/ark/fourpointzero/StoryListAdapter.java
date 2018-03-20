package june.seven.ark.fourpointzero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JAYAN on 20-03-2018.
 */

public class StoryListAdapter extends BaseAdapter
{

    JSONArray jsonArray;
    Context context;

    public StoryListAdapter(Context ctx, JSONArray jsonArray) {
        super();
        this.jsonArray=jsonArray;
        this.context = ctx;
    }


    @Override
    public int getCount()
    {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;

        if(v==null){
            LayoutInflater vi=LayoutInflater.from(context);
            v=vi.inflate(R.layout.story_row,null);

        }
        ImageView iv_cover=v.findViewById(R.id.iv_cover);
        TextView user=v.findViewById(R.id.user_name);

        try
        {
            JSONObject jsonObject=jsonArray.getJSONObject(position);

            Picasso.get().load("http://192.168.137.248:3000/apis/uploads/"+jsonObject.getString("photo")).into(iv_cover);
            user.setText(jsonObject.getString("name"));

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return v;
    }
}
