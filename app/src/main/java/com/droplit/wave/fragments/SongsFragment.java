package com.droplit.wave.fragments;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.droplit.wave.DividerItemDecoration;
import com.droplit.wave.MainActivity;
import com.droplit.wave.R;
import com.droplit.wave.adapters.RecyclerViewAdapter;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Julian on 4/12/2015.
 */
public class SongsFragment extends Fragment {

    private float mActionBarHeight;
    private ActionBar actionBar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_song, container, false);

        final TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        mActionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        actionBar = getActivity().getActionBar();
        
        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.song_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
                .getStringArray(R.array.songs));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getActivity(), "FAB Pressed", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }
    public void onScrollChanged() {

        float y = ((RecyclerView)getActivity().findViewById(R.id.song_view)).getScrollY();
        if (y >= mActionBarHeight && actionBar.isShowing()) {
            actionBar.hide();
        } else if ( y==0 && !actionBar.isShowing()) {
            actionBar.show();
        }
    }
    
}