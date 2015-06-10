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
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Song;

import java.util.ArrayList;

public class AlbumSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Song currSong;
    private final Context mContext;
    private Album mAlbum;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public AlbumSongAdapter(Context c, ArrayList<Song> contents, Album album) {
        mContext = c;
        this.songs = contents;
        songInf = LayoutInflater.from(c);
        mAlbum = album;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {

        final FrameLayout songLay;

        if(pos == 0) {
            songLay = (FrameLayout) songInf.inflate
                    (R.layout.item_album_info, parent, false);
            TextView titleView = (TextView) songLay.findViewById(R.id.album_title);
            TextView artistView = (TextView) songLay.findViewById(R.id.album_artist);

            titleView.setText(mAlbum.getTitle());
            artistView.setText(mAlbum.getArtist() + " | " + mAlbum.getNumSongs() + numSongs());

            return new RecyclerView.ViewHolder(songLay) {
            };
        }

        //map to song layout
        songLay = (FrameLayout) songInf.inflate
                (R.layout.item_song, parent, false);
        //get title and artist views
        TextView songView = (TextView) songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView) songLay.findViewById(R.id.song_artist);
        //get song using position
        currSong = songs.get(pos);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getDuration() + " | " + currSong.getArtist());
        //set position as tag
        songLay.setTag(pos);
        //set position as tag
        final int position = pos;
        songLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, songs.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return new RecyclerView.ViewHolder(songLay) {

        };

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }
    private String numSongs() {
        if (mAlbum.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }


}