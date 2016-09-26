package abhiroj95.com.topprandroid.Fragments;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import abhiroj95.com.topprandroid.DataStore.DBHelper;
import abhiroj95.com.topprandroid.DataStore.Data;
import abhiroj95.com.topprandroid.DataStore.DataContract;
import abhiroj95.com.topprandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteEventDetailFragment extends Fragment {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    Cursor cursor;
    ImageView favMov;
    public FavoriteEventDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_event_detail,container,false);
        dbhelper=new DBHelper(getActivity());
        db=dbhelper.getWritableDatabase();
        cursor=db.rawQuery("select * from " + DataContract.TABLE_NAME + " where " + DataContract.id + "=" + Data.dbposition, null);
        cursor.moveToFirst();
        TextView disp_text=(TextView) view.findViewById(R.id.display_text);
        disp_text.setText(cursor.getString(cursor.getColumnIndex(DataContract.name)));
        ImageView disp_image=(ImageView) view.findViewById(R.id.display_image);
        Picasso.with(getActivity()).load(cursor.getString(cursor.getColumnIndex(DataContract.image))).into(disp_image);
        TextView experience=(TextView) view.findViewById(R.id.experience);
        experience.append(cursor.getString(cursor.getColumnIndex(DataContract.experience)));
        TextView job_desc=(TextView) view.findViewById(R.id.job_desc);
        job_desc.setText("Description:\n"+cursor.getString(cursor.getColumnIndex(DataContract.description)));
        dbhelper=new DBHelper(getActivity());
        db=dbhelper.getWritableDatabase();
        favMov=(ImageView) view.findViewById(R.id.favorite_button);
        Picasso.with(getActivity()).load(R.drawable.star).fit().into(favMov);
        return view;
    }


}
