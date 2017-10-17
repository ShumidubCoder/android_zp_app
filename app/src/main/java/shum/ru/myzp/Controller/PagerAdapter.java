package shum.ru.myzp.Controller;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import shum.ru.myzp.View.FragmentMetersData;
import shum.ru.myzp.View.FragmentMyZP;
import shum.ru.myzp.View.FragmentRenDebit;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentMyZP MyZP = new FragmentMyZP();
                return MyZP;
            case 1:
                FragmentRenDebit Debit = new FragmentRenDebit();
                return Debit;
            case 2:
                FragmentMetersData MetersData = new FragmentMetersData();
                return MetersData;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}


