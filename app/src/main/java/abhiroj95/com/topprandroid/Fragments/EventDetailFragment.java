package abhiroj95.com.topprandroid.Fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import abhiroj95.com.topprandroid.DataStore.Const_Data_Sets;
import abhiroj95.com.topprandroid.DataStore.DBHelper;
import abhiroj95.com.topprandroid.DataStore.Data;
import abhiroj95.com.topprandroid.DataStore.DataContract;
import abhiroj95.com.topprandroid.R;
import abhiroj95.com.topprandroid.Utility.Utility;

/**
 * Created by Abhiroj on 9/24/2016.
 */
public class EventDetailFragment extends Fragment implements View.OnClickListener {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    ContentValues input;
    Data data;
    ImageView favMov;
    public EventDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_event_detail,container,false);
        int position= Const_Data_Sets.position;
        data=Data.dataSet.get(position);
        TextView disp_text=(TextView) view.findViewById(R.id.display_text);
        disp_text.setText(data.name);
        ImageView disp_image=(ImageView) view.findViewById(R.id.display_image);
        Picasso.with(getActivity()).load(data.imagepath).into(disp_image);
        TextView experience=(TextView) view.findViewById(R.id.experience);
        experience.append(data.experience);
        ImageView exp_icon=(ImageView) view.findViewById(R.id.experience_icon);
        Picasso.with(getActivity()).load(R.drawable.calendar).fit().into(exp_icon);
        ImageView ctc_icon=(ImageView) view.findViewById(R.id.ctc_icon);
        Picasso.with(getActivity()).load(R.drawable.money).fit().into(ctc_icon);
        TextView job_desc=(TextView) view.findViewById(R.id.job_desc);
        job_desc.setText("Description:\n"+data.description);
        dbhelper=new DBHelper(getActivity());
        db=dbhelper.getWritableDatabase();
        Cursor c=db.rawQuery("select "+ DataContract.favorite+" from "+ DataContract.TABLE_NAME+" where "+ DataContract.name+"="+"\""+data.name+"\"",null);
        favMov=(ImageView) view.findViewById(R.id.favorite_button);
        if(c.getCount()>0)
        {
            Picasso.with(getActivity()).load(R.drawable.star).fit().into(favMov);
        }
        else {
            Picasso.with(getActivity()).load(R.drawable.dullstar).fit().into(favMov);
            favMov.setOnClickListener(this);
        }
        c.close();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(Utility.ifTableExists(db,DataContract.TABLE_NAME))
        {
            input=new ContentValues();
            input.put(DataContract.name,data.name);
            input.put(DataContract.description,data.description);
            input.put(DataContract.category,data.category);
            input.put(DataContract.experience,data.experience);
            input.put(DataContract.image,data.imagepath);
            input.put(DataContract.id,data.id);
            input.put(DataContract.favorite,"Yes");

            int r_id = (int) db.insert(DataContract.TABLE_NAME, null, input);
            if (r_id == -1) {
                Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
            }
            else {
                Picasso.with(getActivity()).load(R.drawable.star).fit().into(favMov);
                favMov.setOnClickListener(null);
            }
        }
        else{
            Toast.makeText(getActivity(), "Nothing is Bug Free!!", Toast.LENGTH_SHORT).show();
        }
    }
}
