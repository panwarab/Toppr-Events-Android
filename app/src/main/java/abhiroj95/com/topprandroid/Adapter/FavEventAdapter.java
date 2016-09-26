package abhiroj95.com.topprandroid.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import abhiroj95.com.topprandroid.DataStore.DataContract;
import abhiroj95.com.topprandroid.R;

/**
 * Created by Abhiroj on 9/24/2016.
 */
public class FavEventAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Context context;
    Cursor cursor;

    public FavEventAdapter(Context context, Cursor c) {
        super(context, c);
        this.context=context;
        inflater=LayoutInflater.from(context);
        cursor=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.row_layout_event_list, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img=(ImageView) view.findViewById(R.id.event_image);
        String url=cursor.getString(cursor.getColumnIndex(DataContract.image));
        Picasso.with(context).load(url).into(img);
        TextView event_name=(TextView) view.findViewById(R.id.event_name);
        event_name.setText(cursor.getString(cursor.getColumnIndex(DataContract.name)));
        TextView event_cat=(TextView) view.findViewById(R.id.category);
        event_cat.setText(cursor.getString(cursor.getColumnIndex(DataContract.category)));

    }
}
