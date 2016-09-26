package abhiroj95.com.topprandroid.Utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Abhiroj on 9/24/2016.
 */
public class Utility {


    // To check whether table exists in database or not.
    public static boolean ifTableExists(SQLiteDatabase db,String table_name)
    {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ table_name+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

   //Utility Method to check whether device can access internet or not.It is used in main activity to generate alert dialog.
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
