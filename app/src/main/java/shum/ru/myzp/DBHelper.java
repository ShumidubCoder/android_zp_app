package shum.ru.myzp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 10/9/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public final String _ID = "id";


    public DBHelper(Context context) {
        super(context, "DB_ZP_ITEMS", null, 1);
    }


    public DBHelper(Context context, int version) {
        super(context, "DB_ZP_ITEMS", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable (" + _ID + " integer primary key autoincrement not null," +
                "formont text,type text,date text,value text,stsdate text,stsvalue text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
