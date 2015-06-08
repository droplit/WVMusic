package com.droplit.wave;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.droplit.wave.models.Album;
import com.droplit.wave.models.Artist;
import com.droplit.wave.models.Song;

public class OnOverflowSelectedListener implements View.OnClickListener {
    private Album mAlbum;
    private Artist mArtist;
    private Song mSong;

    private Context mContext;

    public OnOverflowSelectedListener(Context context, Artist artist) {
        mContext = context;
        mArtist = artist;
    }
    public OnOverflowSelectedListener(Context context, Album album) {
        mContext = context;
        mAlbum = album;
    }
    public OnOverflowSelectedListener(Context context, Song song) {
        mContext = context;
        mSong = song;
    }

    @Override
    public void onClick(View v) {

        if(mAlbum != null) {
            final PopupMenu popup = new PopupMenu(mContext, v);
            popup.getMenuInflater().inflate(R.menu.menu_album_overflow, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.album_overflow_delete:
                            //deleteAlbum(mAlbum);
                            Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.album_overflow_play:
                            Toast.makeText(mContext, "Play", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.album_overflow_add_to_playlist:
                            Toast.makeText(mContext, "Add to playlist", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.album_overflow_add_to_queue:
                            Toast.makeText(mContext, "Add to queue", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.album_overflow_edit_tags:
                            Toast.makeText(mContext, "Edit tags", Toast.LENGTH_SHORT).show();
                            return true;

                        default:
                            return onMenuItemClick(item);
                    }
                }
            });

            popup.show();
        } else if (mArtist != null) {

        } else if(mSong != null) {

        } else {
            Log.e("ONCLICKERROR", "Not an Album, Artist, or Song!");
            throw new RuntimeException("Not an Album, Artist, or Song!");
        }
    }
}