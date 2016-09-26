package abhiroj95.com.topprandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import abhiroj95.com.topprandroid.DataStore.Const_Data_Sets;
import abhiroj95.com.topprandroid.DataStore.DBHelper;
import abhiroj95.com.topprandroid.DataStore.Data;
import abhiroj95.com.topprandroid.DataStore.DataContract;
import abhiroj95.com.topprandroid.Fragments.EventListFrag;
import abhiroj95.com.topprandroid.Fragments.FavEventFragment;
import abhiroj95.com.topprandroid.Interface.ClickCallback;
import abhiroj95.com.topprandroid.Utility.Utility;


public class MainActivity extends ActionBarActivity implements ClickCallback {


 RequestQueue requestQueue;
    boolean bool;
 String toLaunch="category"; // By Default, launch category
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EC644B")));
        if(savedInstanceState!=null)
        {
            toLaunch=savedInstanceState.getString(Const_Data_Sets.OPT);
        }
        setContentView(R.layout.activity_main);
        if(Utility.isNetworkAvailable(getBaseContext()))
        {
        makeRequest();
            bool=true;
        }
        else
        {
            bool=false;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Snap!!").setMessage("The content on the screen may not be updated or blank due to connectivity issues").setNeutralButton("Ok",null);
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();        }


    }

    //bool used to stop the checking of network connection twice
    @Override
    protected void onResume() {
        if(bool)
        {
        if(Utility.isNetworkAvailable(getBaseContext()))
        {
            makeRequest();
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Snap!!").setMessage("The content on the screen may not be updated or blank due to connectivity issues").setNeutralButton("Ok",null);
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();        }}
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Const_Data_Sets.OPT,toLaunch);
        super.onSaveInstanceState(outState);
    }

    //Method uses Volley API to make Asynchronous API calls.

    public void makeRequest(){
        requestQueue= Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Const_Data_Sets.URL,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try{

                    JSONArray ja=jsonObject.getJSONArray("websites");

                    Data.dataSet=new ArrayList<>();
                    Data.quota_max=Integer.parseInt(jsonObject.getString("quote_max"));
                    Data.quota_available=Integer.parseInt(jsonObject.getString("quote_available"));
                    for(int i=0;i<ja.length();i++)
                    {
                        Data data=new Data();
                        JSONObject pos_element=ja.getJSONObject(i);
                        data.id=pos_element.getString("id");
                        data.name=pos_element.getString("name");
                        data.category=pos_element.getString("category");
                        data.description=pos_element.getString("description");
                        data.experience=pos_element.getString("experience");
                        data.imagepath=pos_element.getString("image");
                        data.dataSet.add(data);
                    }
                     getFragmentManager().beginTransaction().replace(R.id.List_Holder,new EventListFrag()).commitAllowingStateLoss();


                }catch (Exception e)
                {
                    Log.e(Const_Data_Sets.TAG, e.toString());
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(Const_Data_Sets.TAG,volleyError.toString());
                volleyError.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite && !(toLaunch.equals("favorite"))) {
            SQLiteOpenHelper dbHep = new DBHelper(this);
            SQLiteDatabase db = dbHep.getReadableDatabase();
            Cursor cursor = db.query(DataContract.TABLE_NAME, new String[]{DataContract._ID, DataContract.name}, null, null, null, null, null);
            if (cursor.getCount() == 0)
                Toast.makeText(this, "No favorite Events Yet", Toast.LENGTH_SHORT).show();
            else {
                toLaunch="favorite";
                launchFavoritefragment();
            }
            db.close();
            dbHep.close();
            cursor.close();
            return true;
        }

        if(id==R.id.action_category)
        {
            toLaunch="category";
            makeRequest();
        return true;
        }

        if(id==R.id.action_refresh)
        {
            toLaunch="category";  //It is depreceated though, but I am using in just in case.
            makeRequest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(int i) {
        Const_Data_Sets.position=i;
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra("FRAGMENT","Category");
        startActivity(intent);
    }

    @Override
    public void itemfavorite(int eventID) {
       Data.dbposition=eventID;
        Intent i=new Intent(this,DetailActivity.class);
        i.putExtra("FRAGMENT","favorites");
        startActivity(i);
    }


    void launchFavoritefragment(){
        FavEventFragment ff=new FavEventFragment();
        try{
            getFragmentManager().beginTransaction().replace(R.id.List_Holder, ff).commitAllowingStateLoss();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
