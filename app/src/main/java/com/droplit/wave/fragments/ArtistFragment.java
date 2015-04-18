package com.droplit.wave.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.adapters.RecyclerViewAdapter;
import com.github.alexkolpa.fabtoolbar.FabToolbar;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;

public class ArtistFragment extends Fragment {

    private FabToolbar fabToolbar;
    private View playPause;
    private boolean isPlaying = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artists, container, false);

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.artist_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
                .getStringArray(R.array.songs));
        recyclerView.setAdapter(adapter);

        fabToolbar = ((FabToolbar) root.findViewById(R.id.fab_toolbar));

        fabToolbar.setColor(getResources().getColor(R.color.accent));

        fabToolbar.attachToRecyclerView(recyclerView);

        playPause = root.findViewById(R.id.play);


        playPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(!isPlaying) {
                    Toast.makeText(getActivity(), "Playing", Toast.LENGTH_SHORT).show();
                    playPause.setBackgroundResource(R.drawable.ic_pause);
                    fabToolbar.hide();
                }
                else if(isPlaying) {
                    Toast.makeText(getActivity(), "Paused", Toast.LENGTH_SHORT).show();
                    playPause.setBackgroundResource(R.drawable.ic_pause);
                    fabToolbar.hide();
                }

            }
        });
        return root;
    }


}