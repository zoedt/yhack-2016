package hu.ait.android.sprout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hu.ait.android.invest.InvestTipsFragment;
import hu.ait.android.invest.StocksFragment;

/**
 * Created by zoetiet on 11/12/16.
 */
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
                InvestTipsFragment tab1 = new InvestTipsFragment();
                return tab1;
            case 1:
                StocksFragment tab2 = new StocksFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
