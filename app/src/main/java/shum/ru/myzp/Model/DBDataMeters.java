package shum.ru.myzp.Model;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.List;


import shum.ru.myzp.Controller.MyApplication;
import shum.ru.myzp.View.MainActivity;


public class DBDataMeters {


    DBHelperDataMeters sDBHelperDataMeters;
    SQLiteDatabase db;

    Cursor cursor;

    final String TABLE_NAME = "DATA_METERS_TABLE";
    final String VALUE_DATA_METERS_COLUMN= "VALUE";

    List<ItemDataMeters> lstItemDataMeters;

    Context mContext;


    public DBDataMeters(Context context){
        this.mContext = context;
    }

    public List<ItemDataMeters>  dbToArray() {


        if (db == null) {
            sDBHelperDataMeters = new DBHelperDataMeters(mContext);
            db = sDBHelperDataMeters.getReadableDatabase();
        }



        if (db == null)
            Toast.makeText(MyApplication.getAppContext(), "db is null", Toast.LENGTH_SHORT).show();

        cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {

            int columnIndexValue = cursor.getColumnIndex(VALUE_DATA_METERS_COLUMN);

            if (cursor.moveToFirst()) {
                do {
                    String strValue = cursor.getString(columnIndexValue);
                    lstItemDataMeters.add(new ItemDataMeters("strValue"));

                } while (cursor.moveToNext());
            }
        }


        if (!lstItemDataMeters.isEmpty()) return lstItemDataMeters;
        else {
            lstItemDataMeters.add(new ItemDataMeters("01.01 default value if arrValues == null"));
            return lstItemDataMeters;
        }
    }
}
