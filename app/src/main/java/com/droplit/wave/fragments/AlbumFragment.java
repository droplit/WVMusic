package com.droplit.wave.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.adapters.AlbumAdapter;
import com.droplit.wave.models.Album;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AlbumFragment extends Fragment {

    private RecyclerView albumView;

    private ArrayList<Album> mAlbumItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlbumItems = new ArrayList<Album>();
        albumView = (RecyclerView) view.findViewById(R.id.album_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumView.setLayoutManager(layoutManager);
        albumView.setHasFixedSize(true);

        getAlbumList();

        mAdapter = new AlbumAdapter(getActivity().getApplicationContext(), mAlbumItems);
        albumView.setAdapter(mAdapter);

        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),v.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Collections.sort(mAlbumItems, new Comparator<Album>() {
            public int compare(Album a, Album b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

    }



    public void getAlbumList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {
                MediaStore.Audio.AlbumColumns.ARTIST, MediaStore.Audio.AlbumColumns.ALBUM,
                MediaStore.Audio.AlbumColumns.ALBUM_ART, MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                MediaStore.Audio.AlbumColumns.FIRST_YEAR, MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS};
        //final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";

        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri,cursor_cols,null,null,null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int trackNumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_KEY);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM);
            int albumYear = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.FIRST_YEAR);
            String albumArt = musicCursor.getString(musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_ART));


            Drawable img = null;

            if(albumArt != null) {
                img = Drawable.createFromPath(albumArt);
            } else {
                img = getResources().getDrawable(R.drawable.default_artwork);
            }

            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackNumColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(albumYear);
                Log.d("ART", "Album art: " + albumArt);
                mAlbumItems.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, img));
            }

            while (musicCursor.moveToNext());
        }

    }
}