package com.droplit.wave.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.droplit.wave.AlbumActivity;
import com.droplit.wave.Globals;
import com.droplit.wave.MaterialColorPalette;
import com.droplit.wave.OnOverflowSelectedListener;
import com.droplit.wave.R;
import com.droplit.wave.models.Album;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;
    private ArrayList<String> artPaths = new ArrayList<>();

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,final int pos) {
        //map to song layout
        final FrameLayout cardView = (FrameLayout) albumInf.inflate
                (R.layout.list_item_album, parent, false);
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
        artistView.setText(currAlbum.getArtist() + " | " + currAlbum.getNumSongs() + numSongs());
        if(currAlbum.getAlbumArt() != null) {
            coverAlbum.setImageBitmap(BitmapFactory.decodeFile(currAlbum.getAlbumArt()));
            artPaths.add(currAlbum.getAlbumArt());
        } else {
            int color = MaterialColorPalette.randomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(currAlbum.getTitle().substring(0,1).toUpperCase(), color);
            coverAlbum.setImageDrawable(drawable);
        }
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
                Toast.makeText(mContext,albums.get(pos).getTitle(),Toast.LENGTH_SHORT).show();

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