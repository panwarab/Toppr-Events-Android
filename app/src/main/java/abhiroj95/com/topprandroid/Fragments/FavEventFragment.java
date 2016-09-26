package abhiroj95.com.topprandroid.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import abhiroj95.com.topprandroid.Adapter.FavEventAdapter;
import abhiroj95.com.topprandroid.DataStore.Const_Data_Sets;
import abhiroj95.com.topprandroid.DataStore.DBHelper;
import abhiroj95.com.topprandroid.DataStore.Data;
import abhiroj95.com.topprandroid.DataStore.DataContract;
import abhiroj95.com.topprandroid.Interface.ClickCallback;
import abhiroj95.com.topprandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavEventFragment extends Fragment {

    ClickCallback dataListener;


    public FavEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity context) {
        dataListener=(ClickCallback) context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_even_list,container,false);
        ListView eventList=(ListView) view.findViewById(R.id.container_list);
        SQLiteOpenHelper dbhelper=new DBHelper(getActivity());
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try{
            final Cursor c=db.query(DataContract.TABLE_NAME, new String[]{DataContract._ID,DataContract.image, DataContract.id,DataContract.name,DataContract.category}, null, null, null, null,null);
            FavEventAdapter favEventAdapter=new FavEventAdapter(getActivity(),c);
            eventList.setAdapter(favEventAdapter);
            eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(dataListener!=null) {
                        c.moveToPosition(i);
                        dataListener.itemfavorite(Integer.parseInt(c.getString(c.getColumnIndex(DataContract.id))));
                    }
                }
            });
        }
        catch(Exception e)
        {
            Log.e(Const_Data_Sets.TAG,e.toString());
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_frag_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.api_quote) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("%API Quote Available").setMessage("" + (Data.quota_max / Data.quota_available)).setNeutralButton("Ok", null);
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
