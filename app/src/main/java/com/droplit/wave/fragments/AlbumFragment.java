package com.droplit.wave.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

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

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumView.setLayoutManager(layoutManager);
        albumView.setHasFixedSize(true);

        getAlbumList();

        mAdapter = new AlbumAdapter(getActivity().getApplicationContext(), mAlbumItems);
        albumView.setAdapter(mAdapter);

        fastScroller.setRecyclerView(albumView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        albumView.setOnScrollListener(fastScroller.getOnScrollListener());


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
            int artColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);

            //add songs to list
            do {
                String thisArtPath = musicCursor.getString(artColumn);


                /*if(thisArtPath == null) {
                    thisArtPath = BitmapFactory.decodeFile(thisArtPath);
                } else {
                    img = BitmapFactory.decodeResource(getResources(), R.drawable.default_artwork);
                }*/

                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackNumColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(albumYear);
                //Log.d("ART", "Album art: " + thisArtPath);
                mAlbumItems.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, thisArtPath));
            }

            while (musicCursor.moveToNext());
        }

    }
}