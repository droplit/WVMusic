package com.droplit.wave;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.Toolbar;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.droplit.wave.adapters.ViewPagerAdapter;
import com.droplit.wave.ui.activities.SettingsActivity;
import com.droplit.wave.ui.fragments.AlbumFragment;
import com.droplit.wave.ui.fragments.AlbumGridFragment;
import com.droplit.wave.ui.fragments.ArtistFragment;


import com.droplit.wave.ui.fragments.SongsFragment;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;



public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "WVPrefs";
    static final String CURRENT_TAB = "currentTab";

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    private int albumViewType = 0;
    private int albumCols = GridView.AUTO_FIT;
    private SharedPreferences views;

    private Menu mOptionsMenu;

    private GridView gridView;

    private MaterialDialog.Builder materialDialog;

    private DrawerLayout mDrawerLayout;

    private ViewPager viewPager;

    private Toolbar toolbar;

    private String[] titles = new String[]{"Artists", "Albums", "Songs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        /*getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        materialDialog =  new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.app_name)
                .neutralText(R.string.ok);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.primary);

        //getWindow().setStatusBarColor(R.color.primary_dark);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        views = getSharedPreferences(PREFS_NAME, 0);
        albumViewType = views.getInt("albumView", 0);
        albumCols = views.getInt("albumCols", GridView.AUTO_FIT);

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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ArtistFragment(), "Artists");
        if (albumViewType == 0) {
            adapter.addFragment(new AlbumFragment(), "Albums");
        } else {
            adapter.addFragment(new AlbumGridFragment(), "Albums");
        }
        adapter.addFragment(new SongsFragment(), "Songs");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        //TODO: add highlight to the current tab in the navDrawer.
        

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_changelog) {
                            DialogFragment dialogFragment = new ChangelogDialogFragment();
                            dialogFragment.show(getFragmentManager(), "dialog");
                        } else if(menuItem.getItemId() == R.id.nav_settings){
                          Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(i);
                        } else if (menuItem.getItemId() == R.id.nav_about) {

                            //((TextView) findViewById(R.id.about_body)).setText(Html.fromHtml(getResources().getString(R.string.about)));
                            //((TextView) findViewById(R.id.about_links)).setMovementMethod(LinkMovementMethod.getInstance());
                            //((TextView) findViewById(R.id.about_links)).setText(Html.fromHtml(getResources().getString(R.string.about_links)));

                            /*new MaterialDialog.Builder(getApplicationContext())
                                    .title(R.string.action_about)
                                    .customView(R.layout.about_view, true)
                                    .neutralText(R.string.ok)
                                    .show();*/
                            materialDialog.icon(getResources().getDrawable(R.drawable.ic_launcher));

                            materialDialog.show();

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
        mOptionsMenu = menu;

        MenuItem item = menu.findItem(R.id.col_default);

        if(albumCols == GridView.AUTO_FIT) {
            item = menu.findItem(R.id.col_default);
        } else if(albumCols == 1) {
            item = menu.findItem(R.id.col_1);
        } else if(albumCols == 2) {
            item = menu.findItem(R.id.col_2);
        } else if(albumCols == 3) {
            item = menu.findItem(R.id.col_3);
        } else if(albumCols == 4) {
            item = menu.findItem(R.id.col_4);
        } else if(albumCols == 5) {
            item = menu.findItem(R.id.col_5);
        } else if(albumCols == 6) {
            item = menu.findItem(R.id.col_6);
        }

        //item.setChecked(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final SharedPreferences.Editor editor = views.edit();
        gridView = (GridView) viewPager.findViewById(R.id.album_grid_list);

        switch (item.getItemId()) {

            case R.id.view_as_list:
                if(albumViewType == 0) {
                    Toast.makeText(getApplicationContext(), "Already in ListView!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                editor.putInt("albumView",0);
                editor.apply();
                finish();
                startActivity(getIntent());
                return true;
            case R.id.view_as_grid:
                if(albumViewType == 1) {
                    Toast.makeText(getApplicationContext(), "Already in GridView!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                editor.putInt("albumView",1);
                editor.apply();
                finish();
                startActivity(getIntent());
                return true;
            case R.id.view_as_grid_palette:
                if(albumViewType == 2) {
                    Toast.makeText(getApplicationContext(), "Already in GridView!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                editor.putInt("albumView",2);
                editor.apply();
                finish();
                startActivity(getIntent());
                return true;
            case R.id.view_as_grid_card:
                if(albumViewType == 3) {
                    Toast.makeText(getApplicationContext(), "Already in GridView!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                editor.putInt("albumView",3);
                editor.apply();
                finish();
                startActivity(getIntent());
                return true;

            case R.id.col_default:
                gridView.setNumColumns(GridView.AUTO_FIT);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",GridView.AUTO_FIT);
                editor.apply();
                return true;
            case R.id.col_1:
                gridView.setNumColumns(1);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",1);
                editor.apply();
                return true;
            case R.id.col_2:
                gridView.setNumColumns(2);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",2);
                editor.apply();
                return true;
            case R.id.col_3:
                gridView.setNumColumns(3);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",3);
                editor.apply();
                return true;
            case R.id.col_4:
                gridView.setNumColumns(4);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",4);
                editor.apply();
                return true;
            case R.id.col_5:
                gridView.setNumColumns(5);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",5);
                editor.apply();
                return true;
            case R.id.col_6:
                gridView.setNumColumns(6);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                editor.putInt("albumCols",3);
                editor.apply();
                return true;

            case R.id.action_end:
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
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .start(this);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(CURRENT_TAB, viewPager.getCurrentItem());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
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
