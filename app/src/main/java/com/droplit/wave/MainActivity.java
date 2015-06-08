package com.droplit.wave;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.droplit.wave.adapters.ViewPagerAdapter;
import com.droplit.wave.fragments.AlbumFragment;
import com.droplit.wave.fragments.AlbumGridFragment;
import com.droplit.wave.fragments.ArtistFragment;


import com.droplit.wave.fragments.SongsFragment;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.ActionView;
import at.markushi.ui.action.BackAction;
import io.fabric.sdk.android.Fabric;



public class MainActivity extends ActionBarActivity {


    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    private DrawerLayout mDrawerLayout;

    private ViewPager viewPager;

    private Toolbar toolbar;

    private String[] titles = new String[]{"Artists", "Albums", "Songs"};

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go to NowPlaying", Snackbar.LENGTH_LONG)
                        .setAction("NowPlaying", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), NowPlaying.class);
                                startActivity(i);
                            }
                        }).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ArtistFragment(), "Artists");
        adapter.addFragment(new AlbumFragment(), "Albums");
        adapter.addFragment(new AlbumGridFragment(), "AlbumsG");
        adapter.addFragment(new SongsFragment(), "Songs");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_changelog) {
                            DialogFragment dialogFragment = new ChangelogDialogFragment();
                            dialogFragment.show(getFragmentManager(),"dialog");
                        } else if (menuItem.getItemId() == R.id.nav_artists) {
                            viewPager.setCurrentItem(0, true);
                        } else if (menuItem.getItemId() == R.id.nav_albums) {
                            viewPager.setCurrentItem(1, true);
                        } else if (menuItem.getItemId() == R.id.nav_songs) {
                            viewPager.setCurrentItem(2, true);
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_shuffle:
                //shuffle
                return true;
            case R.id.action_end:
                //stopService(playIntent);
                //musicSrv=null;
                System.exit(0);
                return true;
            case R.id.action_about:
                new LibsBuilder()
                        //Pass the fields of your application to the lib so it can find all external lib information
                        .withFields(R.string.class.getFields())
                                //start the activity
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutDescription("This is my Music App.<br /><b>You will find info about what I used to make the app here!</b>")
                        .start(this);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
