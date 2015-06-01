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
import com.droplit.wave.ArtistActivity;
import com.droplit.wave.MainActivity;
import com.droplit.wave.NowPlaying;
import com.droplit.wave.R;
import com.droplit.wave.RecylcerOnClick;
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Artist;
import com.droplit.wave.models.Song;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Artist> artists;
    private LayoutInflater artistInf;
    private Artist currArtist;
    private final Context mContext;

    public ArtistAdapter(Context c, ArrayList<Artist> contents) {
        mContext = c;
        this.artists = contents;
        artistInf = LayoutInflater.from(c);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        //map to song layout
        final FrameLayout cardView = (FrameLayout) artistInf.inflate
                (R.layout.list_item_artist, parent, false);
        //get title and artist views
        TextView artistView = (TextView) cardView.findViewById(R.id.artist_title);
        TextView trackView = (TextView) cardView.findViewById(R.id.artist_tracks);
        //get song using position
        currArtist = artists.get(pos);
        //get title and artist strings
        artistView.setText(currArtist.getArtist());
        trackView.setText(currArtist.getNumSongs() + numSongs());
        //coverAlbum.setImageDrawable(currArtist.getAlbumArt());
        //set position as tag
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ArtistActivity.class);
                i.putExtra("ARTIST_NAME", ((TextView) cardView.findViewById(R.id.artist_title)).getText());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                //Toast.makeText(mContext,((TextView) cardView.findViewById(R.id.artist_title)).getText(),Toast.LENGTH_SHORT).show();
            }
        });


        return new RecyclerView.ViewHolder(cardView) {

        };
    }

    private String numSongs() {
        if (currArtist.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

}