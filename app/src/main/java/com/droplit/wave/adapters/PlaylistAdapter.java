package com.droplit.wave.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.droplit.wave.R;
import com.droplit.wave.models.Playlist;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Playlist> playlists;
    private LayoutInflater playlistInf;
    private Playlist currPlaylist;
    private final Context mContext;


    public PlaylistAdapter(Context c, ArrayList<Playlist> contents) {
        mContext = c;
        this.playlists = contents;
        playlistInf = LayoutInflater.from(c);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,final int pos) {
        //map to song layout
        final FrameLayout playlistView = (FrameLayout) playlistInf.inflate
                (R.layout.list_item_playlist, parent, false);
        //get title and artist views
        TextView playlistName = (TextView) playlistView.findViewById(R.id.playlist_title);

        //get song using position
        currPlaylist = playlists.get(pos);
        //albums.get(pos) = currAlbum.setTag(pos);
        //get title and artist strings
        playlistName.setText(currPlaylist.toString());

        //set position as tag
        playlistView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(mContext, PlaylistActivity.class);
                i.putExtra("PLAYLIST_NAME", playlists.get(pos).toString());
                i.putExtra("PLAYLIST_ID", playlists.get(pos).getId());
                Globals.playlist = playlists.get(pos);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);*/

            }
        });
        //overflowImage.setOnClickListener(new OnOverflowSelectedListener(mContext, albums.get(pos)));



        return new RecyclerView.ViewHolder(playlistView) {

        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

}