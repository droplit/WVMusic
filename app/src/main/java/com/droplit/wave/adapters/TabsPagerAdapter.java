package com.droplit.wave.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.droplit.wave.fragments.ArtistFragment;
import com.droplit.wave.fragments.SongsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = { "Songs", "Artists" };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new SongsFragment();
            case 1:
                // Games fragment activity
                return new ArtistFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}