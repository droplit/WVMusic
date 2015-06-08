package com.droplit.wave.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.droplit.wave.AlbumActivity;
import com.droplit.wave.MaterialColorPalette;
import com.droplit.wave.R;
import com.droplit.wave.models.Album;

import java.util.ArrayList;

public class AlbumGridAdapter extends BaseAdapter {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;
    private ArrayList<String> artPaths = new ArrayList<>();

    public AlbumGridAdapter(Context c, ArrayList<Album> contents) {
        mContext = c;
        this.albums = contents;
        albumInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        //map to song layout
        if (view == null) {
            view = albumInf.inflate(R.layout.list_item_album_grid, parent, false);
        }
        //get title and artist views
        TextView albumView = (TextView) view.findViewById(R.id.album_title);
        TextView artistView = (TextView) view.findViewById(R.id.album_artist);
        final ImageView coverAlbum = (ImageView) view.findViewById(R.id.album_art);
        //get song using position
        currAlbum = albums.get(position);
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AlbumActivity.class);
                i.putExtra("ALBUM_NAME", albums.get(position).getTitle());
                //int position= (Integer)v.getTag();
                i.putExtra("ALBUM_ART", albums.get(position).getAlbumArt());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                Toast.makeText(mContext,albums.get(position).getTitle(),Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private String numSongs() {
        if (currAlbum.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }

}