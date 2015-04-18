package com.droplit.wave.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.droplit.wave.DividerItemDecoration;
import com.droplit.wave.MainActivity;
import com.droplit.wave.R;
import com.droplit.wave.adapters.RecyclerViewAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.RevealColorView;

/**
 * Created by Julian on 4/12/2015.
 */
public class SongsFragment extends Fragment {

    private RevealColorView revealColorView;
    private View selectedView;
    private int backgroundColor;
    private FloatingActionButton fab;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_song, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.song_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        revealColorView = (RevealColorView) root.findViewById(R.id.reveal);
        backgroundColor = Color.parseColor("#ffffff");
        
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
                .getStringArray(R.array.songs));
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.getTop();
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                final int color = getColor();
                final Point p = getLocationInView(revealColorView, v);
                if (selectedView == v) {
                    revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
                    selectedView = null;
                } else {
                    revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 350, null);
                    selectedView = v;

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 350ms
                            //TODO Make this a fragment, not a layout inflater
                            FrameLayout item = (FrameLayout) getActivity().findViewById(R.id.fragment_song);
                            View child = getActivity().getLayoutInflater().inflate(R.layout.fragment_main, null);
                            item.removeAllViews();
                            item.addView(child);
                            Toast.makeText(getActivity(), "This is the Now Playing Screen", Toast.LENGTH_SHORT).show();
                        }
                    }, 350);
                }
            }
        });

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("WV", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    Fragment newFragment = new SongsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_song, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                    return true;
                } else {
                    return false;
                }
            }
        });
        return root;
    }


    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private int getColor() {
        return fab.getColorNormal();
    }

   
    
}