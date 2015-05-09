package com.droplit.wave;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.ViewPager;


import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.droplit.wave.adapters.ViewPagerAdapter;
import com.droplit.wave.fragments.ArtistFragment;
import com.droplit.wave.fragments.SongsFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.mikepenz.aboutlibraries.Libs;

import at.markushi.ui.RevealColorView;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.fabric.sdk.android.Fabric;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener {



    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    private Toolbar toolbar;

    private String[] titles = new String[]{"Songs", "Albums", "Artists"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pager = (ViewPager) this.findViewById(R.id.pager);

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, titles.length);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.revealer);


        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                final int color = R.color.accent;
                int[] coords = new int[2];
                v.getLocationOnScreen(coords);

                if (selectedView == v) {
                    revealColorView.hide(coords[0], coords[1], backgroundColor, 0, 300, null);
                    selectedView = null;
                    //FrameLayout item = (FrameLayout) findViewById(R.id.frame_main);
                    View child = getLayoutInflater().inflate(R.layout.activity_main, null);
                    //item.removeAllViews();
                    //item.addView(child);

                } else {
                    revealColorView.reveal(coords[0], coords[1], color, v.getHeight() / 2, 350, null);
                    selectedView = v;

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 350ms
                            //TODO Make this a fragment, not a layout inflater
                            //FrameLayout item = (FrameLayout) findViewById(R.id.frame_main);
                            View child = getLayoutInflater().inflate(R.layout.fragment_artists, null);
                            //item.removeAllViews();
                            //item.addView(child);
                            Toast.makeText(getApplicationContext(), "This is the Now Playing Screen", Toast.LENGTH_SHORT).show();
                        }
                    }, 350);
                }
            }
        });
        */


        /*floatingActionButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FloatingActionsMenu button = (FloatingActionsMenu) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //button.setBackgroundColor(0x8066bbdd);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {

                    // Do some stuffs here
                    Toast.makeText(getApplicationContext(),"Now Playing!",Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });*/
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabReselected(MaterialTab tab) {

    }
    @Override
    public void onTabUnselected(MaterialTab tab) {

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            new Libs.Builder()
                    //Pass the fields of your application to the lib so it can find all external lib information
                    .withFields(R.string.class.getFields())
                            //start the activity
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription("This is my Music App.<br /><b>You will find info about what I used to make the app here!</b>")
                    .start(this);
        }

        return super.onOptionsItemSelected(item);
    }



}
