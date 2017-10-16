package shum.ru.myzp;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/10/17.
 */

public class SQLDB {


    public final int DB_VERSION = 1;
    public final String TABLE_NAME = "mytable";
    public final String _ID = "id";
    public final String COLUMN_FORMONT = "formont";
    public final String COLUMN_TYPE = "type";
    public final String COLUMN_DATE = "date";
    public final String COLUMN_VALUE = "value";
    public final String COLUMN_STSDATE = "stsdate";
    public final String COLUMN_STSVALUE = "stsvalue";



    DBHelper dbHelper;
    SQLiteDatabase db;


    public String forMounth = "def";
    public String type = "def";
    public String date = "   ";
    public String value = "   ";
    public String stsDate = "";
    public String stsValue = "";


    public SQLDB(Context context) {
        // может быть this

        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }







    public void addItem(String forMounth, String type, String value, String date, String stsDate, String stsValue) {


        if (forMounth == null | forMounth == "") forMounth = this.forMounth;
        if (type == null | type == "") type = this.type;
        if (value == null | value == "") value = this.value;
        if (date == null | date == "") date = this.date;
        if (stsDate == null | stsDate == "") stsDate = this.stsDate;
        if (stsValue == null | stsValue == "") stsValue = this.stsValue;


        // Validator

        SQLiteDatabase db = this.db;

        if (db == null) db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FORMONT, forMounth);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_STSDATE, stsDate);
        cv.put(COLUMN_STSVALUE, stsValue);



        db.insert(TABLE_NAME, null, cv);


       // и вообще в маин активити
        // Toast.makeText(MainActivity, "Added", Toast.LENGTH_LONG).show();



    }


    public void addItemByRowID(String id, String forMounth, String type, String value, String date, String stsDate, String stsValue) {




        if (forMounth == null | forMounth == "") forMounth = this.forMounth;
        if (type == null | type == "") type = this.type;
        if (value == null | value == "") value = this.value;
        if (date == null | date == "") date = this.date;
        if (stsDate == null | stsDate == "") stsDate = this.stsDate;
        if (stsValue == null | stsValue == "") stsValue = this.stsValue;


        if (db == null) db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FORMONT, forMounth);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_STSDATE, stsDate);
        cv.put(COLUMN_STSVALUE, stsValue);



        db.update(TABLE_NAME,cv,"id =" + id, null);





    }


    public void addItemAfterVAlidation(){}

    private void editItem() {}

    private void deleteItem(){}


    public void deleteRowByID(Context context, String [] ids_row) {
        if (this.dbHelper == null) this.dbHelper = new DBHelper(context);
        if (this.db == null) this.dbHelper.getWritableDatabase();


        for (String id_row: ids_row) {
            String [] id_row_for_delete = new String [1];
            id_row_for_delete[0]=id_row;
            db.delete(TABLE_NAME, "id LIKE ?", id_row_for_delete);
            Toast.makeText(context, "row with id " + id_row + " deleted", Toast.LENGTH_SHORT).show();
        }



    }


    public List<MyZPItem> readBDFromStartToEnd(Context context){

        if (this.dbHelper == null)  this.dbHelper = new DBHelper(context);
        if (this.db == null) this.dbHelper.getWritableDatabase();


        Cursor cursor = this.db.query(TABLE_NAME, null, null, null, null, null, null);

        List<MyZPItem> myZPItems = new ArrayList<>();

        if (cursor!=null) {

            boolean cursorMOveToFirst = cursor.moveToFirst();
            if (cursorMOveToFirst) {


                int id_column_index = cursor.getColumnIndex("id");
                int formount_column_index = cursor.getColumnIndex(COLUMN_FORMONT);
                int type_column_index = cursor.getColumnIndex(COLUMN_TYPE);
                int date_column_index = cursor.getColumnIndex(COLUMN_DATE);
                int value_column_index = cursor.getColumnIndex(COLUMN_VALUE);
                int stsdate_column_index = cursor.getColumnIndex(COLUMN_STSDATE);
                int stsvalue_column_index = cursor.getColumnIndex(COLUMN_STSVALUE);


                Log.d("MY_DEBUG", " \n \n Method readBDFromStartToEnd();");

                do {

                    int idFromCursor = cursor.getInt(id_column_index);
                    String strForMonthFromCursor = cursor.getString(formount_column_index);
                    String strTypeFromCursor = cursor.getString(type_column_index);
                    String strDateFromCursor = cursor.getString(date_column_index);
                    String strValueFromCursor =  cursor.getString(value_column_index);
                    String strStsDateFromCursor = cursor.getString(stsdate_column_index);
                    String strStsValueFromCursor = cursor.getString(stsvalue_column_index);



                    Log.d("MY_DEBUG_LOG", "Cursor.getString: "
                            + "id(" + idFromCursor +")"
                            + " " + strForMonthFromCursor
                            + " " + strTypeFromCursor
                            + " " + strDateFromCursor
                            + " " + strValueFromCursor
                            + " " + strStsDateFromCursor
                            + " " + strStsValueFromCursor);





                    myZPItems.add(new MyZPItem(
                            idFromCursor,
                            strForMonthFromCursor,
                            strTypeFromCursor, strDateFromCursor,
                            strValueFromCursor, strStsDateFromCursor,
                            strStsValueFromCursor));

                } while (cursor.moveToNext());



            } else Log.d("DEBUG", "else 1 step. cursor.moveToFirst == " + cursorMOveToFirst);
        } else Log.d("DEBUG", "cursor is null :" + cursor);

        //
        //cursor.close();
        //this.db.close();

        return myZPItems;

    }


}



