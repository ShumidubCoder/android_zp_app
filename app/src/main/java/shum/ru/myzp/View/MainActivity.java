package shum.ru.myzp.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import shum.ru.myzp.Controller.Message;
import shum.ru.myzp.Controller.PagerAdapter;

import shum.ru.myzp.R;



public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FloatingActionButton mainFloatingButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_fragments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFloatingButton = findViewById(R.id.floatingActionButton);
        //mainFloatingButton.setImageResource(R.drawable.ic_add_24);




        //todo maybe disapear or drawer tabs

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My zp"));
        tabLayout.addTab(tabLayout.newTab().setText("Debit"));
        tabLayout.addTab(tabLayout.newTab().setText("Meters data"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // todo maybe errror
        if (id == R.id.et_date) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onMainFloatingButtonClick(View view){

            switch (tabLayout.getSelectedTabPosition()){
                //myzp
                case 0:

                    break;

                //debit
                case 1:

                    break;


                //MetersData
                case 2:

                    break;
            }



    }

}






