package shum.ru.myzp.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                } else if (idsOfSelectedRowsFromDB.contains(idFromDB)){
                    idsOfSelectedRowsFromDB.remove(idFromDB);
                }


                //todo invalidateOptionsMenu();










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
        if (mydb == null) mydb = new SQLDB(getContext());

        mSwipeRefreshLayout = getView().findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //endregion

        initializeData();
        initializeAdapter();





        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        initializeData();
        initializeAdapter();
        super.onResume();
    }

    @Override
    public void onStart() {
        initializeData();
        initializeAdapter();
        super.onStart();
    }

    //region intialize Data and Adapter
    public void initializeData(){
        this.myZPItems = mydb.readBDFromStartToEnd(getContext());

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
                Message.shortToast(getContext(), "position is 0");
                rv.scrollToPosition(this.myZPItems.size() - 1);
                lastViewPosition = 0;
            }
        } else rv.scrollToPosition(this.myZPItems.size() - 1);


        setLastViewAsNull();
    }
    //endregion



    //region onRefresh (for mRecyclerTouchListener)
    @Override
    public void onRefresh() {
        initializeData();
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



    //region alletDialogHelp
    private void alletDialogHelp(){
        final AlertDialog.Builder alertMessage = new AlertDialog.Builder(getContext());
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








