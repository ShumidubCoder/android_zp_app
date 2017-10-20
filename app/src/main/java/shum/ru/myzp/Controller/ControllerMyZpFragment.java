package shum.ru.myzp.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import shum.ru.myzp.R;
import shum.ru.myzp.View.AddItemActivity;
import shum.ru.myzp.View.FragmentMyZP;

import static android.support.v4.app.ActivityCompat.*;
import static java.security.AccessController.getContext;
import static shum.ru.myzp.View.FragmentMyZP.idsOfSelectedRowsFromDB;
import static shum.ru.myzp.View.FragmentMyZP.lastView;
import static shum.ru.myzp.View.FragmentMyZP.mydb;

/**
 * Created by user on 10/17/17.
 */

public class ControllerMyZpFragment {

    //region alletDialogHelp
    public void alletDialogHelp(Context context){
        final AlertDialog.Builder alertMessage = new AlertDialog.Builder(context);
        alertMessage.setMessage(R.string.string_help);
        alertMessage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        alertMessage.create().show();
    }
    //endregion

    public void openEditItemActivity(Context context){
        if (lastView !=null ){

                    //todo епременную tv.id вынести в класс и избавиться от ластВью
                    TextView tvID = lastView.findViewById(R.id.id_from_database);
                    String gettedId = tvID.getText().toString();

                    if (mydb == null) Toast.makeText(context, "mu dn is null", Toast.LENGTH_SHORT).show();
                    else if (mydb != null) {


                        Intent intentForEditRow = new Intent(context, AddItemActivity.class);
                        intentForEditRow.putExtra("id", gettedId);

                        TextView etMounth = lastView.findViewById(R.id.x_mounth);
                        TextView etType = lastView.findViewById(R.id.et_type);
                        TextView etDate = lastView.findViewById(R.id.date_of_paying);
                        TextView etValue = lastView.findViewById(R.id.value_of_paying);
                        TextView etStsDate = lastView.findViewById(R.id.sts_date_of_paying);
                        TextView etStsValue = lastView.findViewById(R.id.sts_value_of_paying);

                        //String month = String.valueOf(Integer.parseInt(etMounth.getText().toString()));
                        String month = (etMounth.getText().toString()).replaceAll("[^\\d]", "");


                        String type =  (etMounth.getText().toString()).replaceAll(month+"_", "");


                        intentForEditRow.putExtra("formonth", month);
                        intentForEditRow.putExtra("type", type);
                        intentForEditRow.putExtra("date", etDate.getText().toString());
                        intentForEditRow.putExtra("value", etValue.getText().toString());
                        intentForEditRow.putExtra("rendate", etStsDate.getText().toString());
                        intentForEditRow.putExtra("renvalue", etStsValue.getText().toString());
                        ((Activity) context).startActivityForResult(intentForEditRow, 200);


                    }


                } else Message.shortToast(context, "lastView == null");



    }


    public void openAddItemActivity(Context context){
        Intent intent = new Intent( context, AddItemActivity.class);
        ((Activity) context).startActivity(intent);
    }


    public void deleteItems(Context context){

        if (!idsOfSelectedRowsFromDB.isEmpty()){

                    String [] ids_row = (String []) idsOfSelectedRowsFromDB.toArray(new String [idsOfSelectedRowsFromDB.size()]);

                    if (mydb != null){

                        mydb.deleteRowByID(context, ids_row);
                        idsOfSelectedRowsFromDB.clear();



                    }





        }


    }


}
