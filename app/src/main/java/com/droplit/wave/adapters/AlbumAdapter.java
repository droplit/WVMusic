package com.droplit.wave.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droplit.wave.AlbumActivity;
import com.droplit.wave.MainActivity;
import com.droplit.wave.NowPlaying;
import com.droplit.wave.R;
import com.droplit.wave.RecylcerOnClick;
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Song;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;

    public AlbumAdapter(Context c, ArrayList<Album> contents) {
        mContext = c;
        this.albums = contents;
        albumInf = LayoutInflater.from(c);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        //map to song layout
        final FrameLayout cardView = (FrameLayout) albumInf.inflate
                (R.layout.list_item_card_small, parent, false);
        //get title and artist views
        TextView albumView = (TextView) cardView.findViewById(R.id.album_title);
        TextView artistView = (TextView) cardView.findViewById(R.id.album_artist);
        ImageView coverAlbum = (ImageView) cardView.findViewById(R.id.album_art);
        //get song using position
        currAlbum = albums.get(pos);
        //get title and artist strings
        albumView.setText(currAlbum.getTitle());
        artistView.setText(currAlbum.getArtist() + " | " + currAlbum.getNumSongs() + numSongs());
        //if(currAlbum.getAlbumArt() != null) {
        coverAlbum.setImageDrawable(currAlbum.getAlbumArt());
        //set position as tag
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AlbumActivity.class);
                i.putExtra("ALBUM_NAME", ((TextView) cardView.findViewById(R.id.album_title)).getText());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                Toast.makeText(mContext,((TextView) cardView.findViewById(R.id.album_title)).getText(),Toast.LENGTH_SHORT).show();

            }
        });


        return new RecyclerView.ViewHolder(cardView) {

        };
    }

    private String numSongs() {
        if (currAlbum.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        
    }

}