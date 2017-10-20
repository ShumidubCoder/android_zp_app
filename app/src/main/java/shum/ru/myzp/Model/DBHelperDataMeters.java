package shum.ru.myzp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 10/20/17.
 */

public class DBHelperDataMeters extends SQLiteOpenHelper {

    final String TABLE_NAME = "DATA_METERS_TABLE";
    final String _ID = "id"; //todo see it
    final String VALUE_DATA_METERS_COLUMN= "VALUE";


     public DBHelperDataMeters(Context context) {
        super(context, "DATA_METERS_DB", null, 1);
     }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table DATA_METERS_TABLE (id integer primary key autoincrement not null,VALUE text);");





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
