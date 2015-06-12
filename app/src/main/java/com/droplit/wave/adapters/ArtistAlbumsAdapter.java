package com.droplit.wave.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.droplit.wave.AlbumActivity;
import com.droplit.wave.Globals;
import com.droplit.wave.OnOverflowSelectedListener;
import com.droplit.wave.R;
import com.droplit.wave.models.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistAlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;

    public ArtistAlbumsAdapter(Context c, ArrayList<Album> contents) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,final int pos) {
        //map to song layout
        final FrameLayout cardView = (FrameLayout) albumInf.inflate
                (R.layout.list_item_artist_album, parent, false);
        //get title and artist views
        TextView albumView = (TextView) cardView.findViewById(R.id.album_title);
        TextView artistView = (TextView) cardView.findViewById(R.id.album_artist);
        final ImageView coverAlbum = (ImageView) cardView.findViewById(R.id.album_art);
        ImageView overflowImage = (ImageView) cardView.findViewById(R.id.album_overflow);
        //get song using position
        currAlbum = albums.get(pos);
        //albums.get(pos) = currAlbum.setTag(pos);
        //get title and artist strings
        albumView.setText(currAlbum.getTitle());
        artistView.setText(currAlbum.getNumSongs() + numSongs());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(currAlbum.getTitle());
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(currAlbum.getTitle().substring(0,1).toUpperCase(), color);
        Picasso.with(mContext)
                .load("file://" + currAlbum.getAlbumArt())
                .placeholder(drawable)
                .error(drawable)
                .into(coverAlbum);

        //set position as tag
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AlbumActivity.class);
                i.putExtra("ALBUM_NAME", albums.get(pos).getTitle());
                //int position= (Integer)v.getTag();
                i.putExtra("ALBUM_ART", albums.get(pos).getAlbumArt());
                Globals.album = albums.get(pos);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });
        overflowImage.setOnClickListener(new OnOverflowSelectedListener(mContext, albums.get(pos)));



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