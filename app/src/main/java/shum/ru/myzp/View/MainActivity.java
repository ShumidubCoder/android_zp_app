package shum.ru.myzp.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;

import shum.ru.myzp.Controller.ControllerMyZpFragment;
import shum.ru.myzp.Controller.PagerAdapter;

import shum.ru.myzp.Model.DBDataMeters;
import shum.ru.myzp.Model.DBHelperDataMeters;
import shum.ru.myzp.Model.ItemDataMeters;
import shum.ru.myzp.Model.SPhelper;
import shum.ru.myzp.R;

import static shum.ru.myzp.View.FragmentMyZP.idsOfSelectedRowsFromDB;
import static shum.ru.myzp.View.MainActivity.allAddFABSInvisible;


public class MainActivity extends AppCompatActivity {

    static TabLayout tabLayout;
    FloatingActionButton mainFloatingButton;
    FloatingActionButton mainFloatingButtonMini;
    static LinearLayout ll_myZP_add_FAB;
    LinearLayout llDataMetersFAB;
    static LinearLayout llHelpFAB;
    static LinearLayout ll_my_zp_edit_and_delete_FABS ;
    static LinearLayout ll_data_meter_edit_amd_delete_FABS ;
    boolean fabMenuIsClicked;
    boolean actionDownIsActive;
    static Animation openAlpha;
    static Animation hideAlpha;
    ControllerMyZpFragment mControllerMyZpFragment;
    List<LinearLayout> mLinearLayoutListFABS;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_fragments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFloatingButton = findViewById(R.id.floatingActionButton);
        mainFloatingButtonMini = findViewById(R.id.floatingActionButtonMini);
        ll_myZP_add_FAB = findViewById(R.id.ll_my_zp_add_FABS);
        llDataMetersFAB = findViewById(R.id.ll_data_meter_FABS);
        llHelpFAB = findViewById(R.id.ll_help_fab);

        ll_myZP_add_FAB.setVisibility(View.INVISIBLE);
        llHelpFAB.setVisibility(View.INVISIBLE);
        llDataMetersFAB.setVisibility(View.INVISIBLE);


        mControllerMyZpFragment = new ControllerMyZpFragment();


        ll_my_zp_edit_and_delete_FABS = findViewById(R.id.ll_my_zp_add_edit_and_delete_FABS);
        ll_data_meter_edit_amd_delete_FABS = findViewById(R.id.ll_data_meter_add_edit_and_delete_FABS);

        mLinearLayoutListFABS = Arrays.asList(ll_myZP_add_FAB, llDataMetersFAB,llHelpFAB,
                ll_my_zp_edit_and_delete_FABS, ll_data_meter_edit_amd_delete_FABS);

        openAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_fab_open);
        hideAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_fab_hide);

        mainFloatingButtonMini.setVisibility(View.INVISIBLE);




        //todo maybe disapear or drawer tabs

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My zp"));
        tabLayout.addTab(tabLayout.newTab().setText("Debit"));
        tabLayout.addTab(tabLayout.newTab().setText("Meters data"));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tabLayout.getSelectedTabPosition() == 1) mainFloatingButton.setVisibility(View.INVISIBLE);
                else if (tabLayout.getSelectedTabPosition() == 2){
                    mainFloatingButton.setVisibility(View.INVISIBLE);
                    mainFloatingButtonMini.setVisibility(View.VISIBLE);
                }
                else {
                    mainFloatingButton.setVisibility(View.VISIBLE);
                    setAddAndDeleteFABSEnable();
                }

                dissaperEditAndDeleteOnUnclickedWhenChangeTAb();
                if (fabMenuIsClicked) switcherFABsByTabs();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                dissaperEditAndDeleteOnUnclickedWhenChangeTAb();
                if (fabMenuIsClicked) switcherFABsByTabs();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                dissaperEditAndDeleteOnUnclickedWhenChangeTAb();
                if (fabMenuIsClicked) switcherFABsByTabs();
            }
        });


        allAddFABSInvisible();
        setAddAndDeleteFABSEnable();





    }


    //region appear/disapear FAB

    //appear/disapear FAB
    public void dissaperEditAndDeleteOnUnclickedWhenChangeTAb(){
        if (tabLayout.getSelectedTabPosition() != 0 || tabLayout.getSelectedTabPosition() == 1) {
            idsOfSelectedRowsFromDB.clear();
            setAddAndDeleteFABSEnable();
        }
    }
    //appear/disapear FAB
    public static void allAddFABSInvisible() {

        //ll_myZP_add_FAB.setVisibility(View.INVISIBLE);
        //llDataMetersFAB.setVisibility(View.INVISIBLE);
        llHelpFAB.setVisibility(View.INVISIBLE);
        ll_my_zp_edit_and_delete_FABS.setVisibility(View.INVISIBLE);
        ll_data_meter_edit_amd_delete_FABS.setVisibility(View.INVISIBLE);
    }
    //appear/disapear FAB
    public void switcherFABsByTabs(){

        switch (tabLayout.getSelectedTabPosition()) {
            //myzp
            case 0:

                mainFloatingButton.setVisibility(View.VISIBLE);
                mainFloatingButtonMini.setVisibility(View.INVISIBLE);

                if (fabMenuIsClicked) disapperaFabSubmenu();
                else if (!fabMenuIsClicked) showFabSubmenu();
                break;

            //debit - can be hidden
            case 1:
                break;

            //MetersData
            case 2:

               mainFloatingButton.setVisibility(View.INVISIBLE);
               mainFloatingButtonMini.setVisibility(View.VISIBLE);

                if (fabMenuIsClicked) disapperaFabSubmenu();
                else if (!fabMenuIsClicked) showFabSubmenu();
                break;
        }
    }
    //appear/disapear FAB
    public void showFabSubmenu() {

           if (tabLayout.getSelectedTabPosition() == 0){

            ll_myZP_add_FAB.startAnimation(openAlpha);
            llHelpFAB.startAnimation(openAlpha);

            ll_myZP_add_FAB.setVisibility(View.VISIBLE);
            llHelpFAB.setVisibility(View.VISIBLE);
        }
        else if (tabLayout.getSelectedTabPosition() == 2){


            llDataMetersFAB.startAnimation(openAlpha);
            llDataMetersFAB.setVisibility(View.VISIBLE);
        }


        fabMenuIsClicked = true;
    }
    //appear/disapear FAB
    public void disapperaFabSubmenu() {

        for (LinearLayout llFabs : mLinearLayoutListFABS){
            if (llFabs.getVisibility() == View.VISIBLE){
                llFabs.startAnimation(hideAlpha);
                llFabs.setVisibility(View.INVISIBLE);
            }

        }


//
//        ll_myZP_add_FAB.setVisibility(View.INVISIBLE);
//        llHelpFAB.setVisibility(View.INVISIBLE);
//        llDataMetersFAB.setVisibility(View.INVISIBLE);


        fabMenuIsClicked = false;
    }
    //appear/disapear FAB
    public static void setAddAndDeleteFABSEnable(){



// todo  try this
        if(tabLayout.getSelectedTabPosition() == 0){

            if(idsOfSelectedRowsFromDB == null || idsOfSelectedRowsFromDB.isEmpty()){
                SetAlphaMyZPFABSEditAndDelete();
                ll_my_zp_edit_and_delete_FABS.setVisibility(View.INVISIBLE);


            }else if(!idsOfSelectedRowsFromDB.isEmpty()){
                SetAlphaMyZPFABSEditAndDelete();
                ll_my_zp_edit_and_delete_FABS.setVisibility(View.VISIBLE);

            }
        } else if (tabLayout.getSelectedTabPosition() == 2){

            //todo now leave as so

        }
    }
    //appear/disapear FAB
    public static void SetAlphaMyZPFABSEditAndDelete (){


        if(idsOfSelectedRowsFromDB == null || idsOfSelectedRowsFromDB.isEmpty()){
            if(ll_my_zp_edit_and_delete_FABS.getVisibility() == View.VISIBLE) ll_my_zp_edit_and_delete_FABS.startAnimation(hideAlpha);



        }else if(!idsOfSelectedRowsFromDB.isEmpty()){
            //if(ll_my_zp_edit_and_delete_FABS.getVisibility() == View.VISIBLE)
                ll_my_zp_edit_and_delete_FABS.startAnimation(openAlpha);


        }


    }

    //endregion



    //region click

    //click
    public void onMainFloatingButtonClick(View view) {
        allAddFABSInvisible();
        setAddAndDeleteFABSEnable();
        switcherFABsByTabs();
    }
    //click
    public void onHelpSubmenuFABClick(View view) {
        if (tabLayout.getSelectedTabPosition() == 0){
            mControllerMyZpFragment.alletDialogHelp(this);
        }
    }
    //click
    public void onDeleteSubmenuFABClick(View view) {
        if (tabLayout.getSelectedTabPosition() == 0){
            mControllerMyZpFragment.deleteItems(this);
            //invalidateOptionsMenu();
            FragmentMyZP.initializeData(this);
            FragmentMyZP.initializeAdapter();
        } else if (tabLayout.getSelectedTabPosition() == 0) {
            // todo delete
        }
    }
    //click
    public void onEditSubmenuFABClick(View view) {
        if (tabLayout.getSelectedTabPosition() == 0){
            mControllerMyZpFragment.openEditItemActivity(this);
        } else if (tabLayout.getSelectedTabPosition() == 0) {
            // todo edit
        }
    }
    //click
    public void onAddSubmenuFABClick(View view) {
        if (tabLayout.getSelectedTabPosition() == 0){
            mControllerMyZpFragment.openAddItemActivity(this);
        } else if (tabLayout.getSelectedTabPosition() == 0) {
            // todo add
        }
    }

    public void onMainFloatingButtonClickMini(View view) {

        String dataMetersText;
        TextView tvCoolText = findViewById(R.id.tvCoolText);
        EditText edEditString = findViewById(R.id.etDataMeters);
        final String KEY_DATA_METERS_STRING = "DATA_METERS_STRING_VALUE";
        ScrollView csDataMeters = (ScrollView) findViewById(R.id.svDAtaMeters);
        dataMetersText = SPhelper.getSharedPreference(this, KEY_DATA_METERS_STRING, "" );
        mainFloatingButtonMini.setRippleColor(Color.RED);


        if (!edEditString.getText().toString().isEmpty()){
            dataMetersText = dataMetersText + "\n" + edEditString.getText().toString();
            SPhelper.putSharedPreference(this, KEY_DATA_METERS_STRING, dataMetersText);

            tvCoolText.setText(dataMetersText);
            edEditString.setText("");

        }else if (edEditString.getText().toString().isEmpty()) {

            tvCoolText.setText(dataMetersText);
            Toast.makeText(this, "enter the text", Toast.LENGTH_SHORT).show();

        }



        csDataMeters.fullScroll(ScrollView.FOCUS_DOWN);




    }

    //endregion





}






//todo alphaFast





