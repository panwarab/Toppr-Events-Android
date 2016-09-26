package abhiroj95.com.topprandroid.DataStore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abhiroj on 9/24/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAM="datahack";

    public static final int DB_VERSION=1;

    public DBHelper(Context context){
        super(context,DB_NAM,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String exec="CREATE TABLE "+ DataContract.TABLE_NAME
                +" ("+ DataContract._ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.id+
                " TEXT NOT NULL, "
                + DataContract.name
                +" TEXT NOT NULL, "+ DataContract.category
                +" TEXT NOT NULL, "+ DataContract.description
                +" TEXt NOT NULL, "+ DataContract.favorite
                +" TEXT NOT NULL, "+ DataContract.image
                +" TEXT NOT NULL, "+DataContract.experience+" TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(exec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
