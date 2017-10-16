package shum.ru.myzp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    LinearLayout llTableTitle;
    LinearLayout llEmpty;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView rv;
    RecyclerView.LayoutManager rvLayoutManager;
    RecyclerView.Adapter rvAdapter;

    public SQLDB mydb;

    public List<MyZPItem> myZPItems;
    public ArrayList<String> idsOfSelectedRowsFromDB;

    View lastView;
    int lastViewPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region initialize varables and set clickListener
        llEmpty = findViewById(R.id.ll_empty_state);
        llTableTitle = findViewById(R.id.ll_table_title);
        rv = findViewById(R.id.rv);
        rvLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLayoutManager);

        //region onItemrecyclerViewListener
        rv.addOnItemTouchListener(new RecyclerTouchListener(this,
                rv, new ClickListener() {


            @Override
            public void onClick(View view, final int position) {

                lastView = view;
                lastViewPosition = position;


                setAlpha(view, position);



                TextView tvID = view.findViewById(R.id.id_from_database);
                String idFromDB = tvID.getText().toString();

                if (idsOfSelectedRowsFromDB == null
                        || !idsOfSelectedRowsFromDB.contains(idFromDB)){
                    idsOfSelectedRowsFromDB.add(idFromDB);
                } else if (idsOfSelectedRowsFromDB.contains(idFromDB)){
                    idsOfSelectedRowsFromDB.remove(idFromDB);
                }


                invalidateOptionsMenu();










//
//                Toast.makeText(MainActivity.this, "Single Click on item with id        :"+idFromDB,
//                        Toast.LENGTH_SHORT).show();
            }




            public void setAlpha(View view, int position){




                if (rv.getChildAdapterPosition(view) == position) {


                    if (view.getAlpha() != 1f)  view.setAlpha(1f);
                    else   view.setAlpha(0.5f);


                }else if (rv.getChildAdapterPosition(view) != position){
                   view.setAlpha(1f);
                }
            }









            @Override
            public void onLongClick(View view, int position) {

                TextView tvID = view.findViewById(R.id.id_from_database);
                String idFromDB = tvID.getText().toString();

//                Toast.makeText(MainActivity.this, "Long Click on item with id        :"+ idFromDB,
//                        Toast.LENGTH_LONG).show();
            }



        }));



        //endregion

        idsOfSelectedRowsFromDB = new ArrayList<String>(){};
        if (mydb == null) mydb = new SQLDB(this);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //endregion

        initializeData();
        initializeAdapter();
    }

    //region onRestart and OnResume
    @Override
    protected void onRestart() {
        initializeData();
        initializeAdapter();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        initializeData();
        initializeAdapter();
        super.onResume();
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200){
            this.idsOfSelectedRowsFromDB.clear();
            invalidateOptionsMenu();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    //region OptionsMenu creation, prepare, onClickItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_group_main_mode, idsOfSelectedRowsFromDB_IsEmpty());
        menu.setGroupVisible(R.id.menu_group_edit_and_delete_mode, !idsOfSelectedRowsFromDB_IsEmpty());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.menu_item_help:
                alletDialogHelp();
                break;
            case R.id.menu_item_add:
                startActivity(new Intent(this, AddItemActivity.class));
                break;




            case R.id.menu_item_edit:
                if (lastView !=null ){

                    //todo епременную tv.id вынести в класс и избавиться от ластВью
                    TextView tvID = lastView.findViewById(R.id.id_from_database);
                    String gettedId = tvID.getText().toString();



                    if (mydb == null) Toast.makeText(this, "mu dn is null", Toast.LENGTH_SHORT).show();
                    else if (mydb != null) {


                        Intent intentForEditRow = new Intent(this, AddItemActivity.class);
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
                        startActivityForResult(intentForEditRow, 200);


                    }


                } else Message.shortToast(this, "lastView == null");
                break;
            case R.id.menu_item_delete:
                if (!idsOfSelectedRowsFromDB.isEmpty()){

                    String [] ids_row = (String []) idsOfSelectedRowsFromDB.toArray(new String [idsOfSelectedRowsFromDB.size()]);

                    if (mydb != null){

                        mydb.deleteRowByID(this, ids_row);
                        idsOfSelectedRowsFromDB.clear();

                        invalidateOptionsMenu();
                        initializeData();
                        initializeAdapter();

                    }







                    }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    //region boolean idsOfSelectedRowsFromDB_IsEmpty
    private boolean idsOfSelectedRowsFromDB_IsEmpty(){
        if (idsOfSelectedRowsFromDB == null || idsOfSelectedRowsFromDB.isEmpty()) return true;
        else return false;
    }
    //endregion



    //region onRefresh (for mRecyclerTouchListener)
    @Override
    public void onRefresh() {
        initializeData();
        initializeAdapter();
        idsOfSelectedRowsFromDB.clear();
        invalidateOptionsMenu();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 700);
    }
    //endregion


    //region setAlphForView
    public void setAlphaForView(View view) {
        if (view.getAlpha() != 1f) view.setAlpha(1f);
        else view.setAlpha(0.5f);
    }
    //endregion


    //region setEmptyState
    public void setEmptyState(boolean isSet){
        if (isSet) {
            llTableTitle.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        } else if (!isSet){
            llTableTitle.setVisibility(View.VISIBLE);
            rv.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
        }
    }
    //endregion


    //region intialize Data and Adapter
    public void initializeData(){
        this.myZPItems = mydb.readBDFromStartToEnd(MainActivity.this);

        if (myZPItems.isEmpty()) setEmptyState(true);
        else setEmptyState(false);
    }

    public void initializeAdapter() {
        rvAdapter = new RVAdapter(this.myZPItems);
        rv.setAdapter(rvAdapter);

        if (lastViewPosition != 0){
           try {
               rv.scrollToPosition(lastViewPosition);
               lastView = null;
               lastViewPosition = 0;
           }catch(NullPointerException e){
               Message.shortToast(this, "position is 0");
               rv.scrollToPosition(this.myZPItems.size() - 1);
               lastViewPosition = 0;
           }
        } else rv.scrollToPosition(this.myZPItems.size() - 1);


        setLastViewAsNull();
    }
    //endregion


    //region alletDialogHelp
    private void alletDialogHelp(){
        final AlertDialog.Builder alertMessage = new AlertDialog.Builder(MainActivity.this);
        alertMessage.setMessage(R.string.string_help);
        alertMessage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        alertMessage.create().show();
    }
    //endregion


    public void setLastViewAsNull() {
        if(lastView != null)lastView = null;

    }



}


// todo backStack by backButton, position lastView , id by 1 again if 1 delete, if edit - edit only one.
// todo ids log and




