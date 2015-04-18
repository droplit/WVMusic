package com.droplit.wave.fragments;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
                fabClick(v);
            }
        });
        return root;
    }

    public void fabClick(View v) {
        final int color = getColor();
        final Point p = getLocationInView(revealColorView, v);

        FrameLayout item = (FrameLayout)getActivity().findViewById(R.id.fragment_song);
        View child = getActivity().getLayoutInflater().inflate(R.layout.fragment_main, null);

        if (selectedView == v) {
            revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
            selectedView = null;

            item.removeView(child);

        } else {
            revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
            selectedView = v;
            item.addView(child);
        }

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