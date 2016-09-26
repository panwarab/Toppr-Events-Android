package abhiroj95.com.topprandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import abhiroj95.com.topprandroid.DataStore.Data;
import abhiroj95.com.topprandroid.R;

/**
 * Created by Abhiroj on 9/24/2016.
 */
public class EventListAdapter extends ArrayAdapter<Data> {
    public EventListAdapter(Context context, List<Data> dataSet) {
        super(context,0,dataSet);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Data data=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.row_layout_event_list,parent,false);
        }

        ImageView imageView=(ImageView)convertView.findViewById(R.id.event_image);
        Picasso.with(getContext()).load(data.imagepath).resize(50,50).centerCrop().into(imageView);
        TextView event_name=(TextView) convertView.findViewById(R.id.event_name);
        event_name.setText(data.name);
        TextView event_cat=(TextView) convertView.findViewById(R.id.category);
        event_cat.setText(data.category);
        ImageView arrow=(ImageView) convertView.findViewById(R.id.arrow);
        Picasso.with(getContext()).load(R.drawable.arrow).resize(32, 32).into(arrow);
        return convertView;
    }

}

