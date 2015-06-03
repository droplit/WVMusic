package com.droplit.wave.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.models.Song;

import java.util.ArrayList;

public class ArtistSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Song currSong;
    private final Context mContext;

    public ArtistSongAdapter(Context c, ArrayList<Song> contents) {
        mContext = c;
        this.songs = contents;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int pos) {
        //map to song layout
        final FrameLayout songLay = (FrameLayout) songInf.inflate
                (R.layout.item_song, parent, false);
        //get title and artist views
        TextView songView = (TextView) songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView) songLay.findViewById(R.id.song_artist);
        //get song using position
        currSong = songs.get(pos);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getDuration() + " - " + currSong.getAlbumName());
        //set position as tag
        songLay.setTag(pos);
        //set position as tag
        songLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, songs.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        return new RecyclerView.ViewHolder(songLay) {

        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }



}