package shum.ru.myzp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shum.ru.myzp.Controller.ClickListener;
import shum.ru.myzp.Controller.Message;
import shum.ru.myzp.Controller.RVAdapter;
import shum.ru.myzp.Controller.RecyclerTouchListener;
import shum.ru.myzp.Model.MyZPItem;
import shum.ru.myzp.Model.SQLDB;
import shum.ru.myzp.R;

public class FragmentMyZP extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {


    static LinearLayout llTableTitle;
    static LinearLayout llEmpty;
    SwipeRefreshLayout mSwipeRefreshLayout;

    static RecyclerView rv;
    RecyclerView.LayoutManager rvLayoutManager;
    static RecyclerView.Adapter rvAdapter;

    public static SQLDB mydb;

    public static List<MyZPItem> myZPItems;
    public static ArrayList<String> idsOfSelectedRowsFromDB;

    public static View lastView;
    static int lastViewPosition = 0;

    int countClick;


    int sumValue;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_my_z, container, false);




        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        //region initialize varables and set clickListener
        llEmpty = getView().findViewById(R.id.ll_empty_state);
        llTableTitle = getView().findViewById(R.id.ll_table_title);
        rv = getView().findViewById(R.id.rv);
        rvLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(rvLayoutManager);



        //region onItemrecyclerViewListener
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
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

                    if(idsOfSelectedRowsFromDB.size() == 1) countClick=1;
                    if (countClick == 1) showSumm(view);

                } else if (idsOfSelectedRowsFromDB.contains(idFromDB)){
                    idsOfSelectedRowsFromDB.remove(idFromDB);
                    countClick =0;
                }


                if (MainActivity.ll_myZP_add_FAB.getVisibility() == View.VISIBLE) {
                        MainActivity.setAddAndDeleteFABSEnable();
                }

                //showSumm(view);



                //todo invalidateOptionsMenu();  todo edit and delete as group and i ftrue visible or not







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

             //pass
            }



        }));



        //endregion

        idsOfSelectedRowsFromDB = new ArrayList<String>(){};
        if (mydb == null) mydb = new SQLDB(getContext());

        mSwipeRefreshLayout = getView().findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //endregion

        initializeData(getContext());
        initializeAdapter();





        super.onViewCreated(view, savedInstanceState);
    }

    public void showSumm(View view){
        int sumValueResult = 0;

        TextView tvValue = view.findViewById(R.id.value_of_paying);
        if (sumValue == 0) sumValue = Integer.parseInt(tvValue.getText().toString());
        else if (sumValue != 0){
            sumValue = sumValue + Integer.parseInt(tvValue.getText().toString());
            sumValueResult = sumValue;
            sumValue = 0;
        }


        double d = (sumValueResult/0.87);
        int sumValueResultAndTax = (int) d;

        if (idsOfSelectedRowsFromDB.size() == 2) {
            Toast.makeText(getContext(), "Value =   " + sumValueResult + "\nValue + Tax =   " + sumValueResultAndTax,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        initializeData(getContext());
        initializeAdapter();
        super.onResume();
    }

    @Override
    public void onStart() {
        initializeData(getContext());
        initializeAdapter();
        super.onStart();
    }

    //region intialize Data and Adapter
    public void initializeData(){
        myZPItems = mydb.readBDFromStartToEnd(getContext());

        if (myZPItems.isEmpty()) setEmptyState(true);
        else setEmptyState(false);
    }

    public static void initializeData(Context context){

        myZPItems = mydb.readBDFromStartToEnd(context);

        if (myZPItems.isEmpty()) setEmptyState(true);
        else setEmptyState(false);
    }

    public static void initializeAdapter() {
        rvAdapter = new RVAdapter(myZPItems);
        rv.setAdapter(rvAdapter);

        if (lastViewPosition != 0){
            try {
                rv.scrollToPosition(lastViewPosition);
                lastView = null;
                lastViewPosition = 0;
            }catch(NullPointerException e){
                Message.debugLog("position is 0");
                rv.scrollToPosition(myZPItems.size() - 1);
                lastViewPosition = 0;
            }
        } else rv.scrollToPosition(myZPItems.size() - 1);

        setLastViewAsNull();
    }
    //endregion



    //region onRefresh (for mRecyclerTouchListener)
    @Override
    public void onRefresh() {
        initializeData(getContext());
        initializeAdapter();
        idsOfSelectedRowsFromDB.clear();
        // todo invalidateOptionsMenu();

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
    public static void setEmptyState(boolean isSet){
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






    public static void setLastViewAsNull() {
        if(lastView != null)lastView = null;

    }





}







//todo не очистилось
