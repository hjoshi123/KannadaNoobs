package com.example.android.kannada;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by root on 7/1/17.
 */

public class CategoriesAdapter extends FragmentPagerAdapter {

    private String tabTitle[] = new String[]{"Numbers","Colors","Family","Phrases"};

    public CategoriesAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new NumbersFragment();
        else if(position == 1)
            return new ColorsFragment();
        else if(position == 2)
            return new FamilyFragment();
        else
            return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

}
