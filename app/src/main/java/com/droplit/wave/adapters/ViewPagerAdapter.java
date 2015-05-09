package com.droplit.wave.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.droplit.wave.fragments.AlbumFragment;
import com.droplit.wave.fragments.ArtistFragment;
import com.droplit.wave.fragments.SongsFragment;

/**
 * Created by Julian on 5/8/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        ArtistFragment tab1 = new ArtistFragment();

        switch (position) {
            case 0:
                return tab1;
            case 1:
                AlbumFragment tab2 = new AlbumFragment();
                return tab2;
            case 2:
                SongsFragment tab3 = new SongsFragment();
                return tab3;

        }
        return tab1;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}