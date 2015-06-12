package com.droplit.wave.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.adapters.SongAdapter;
import com.droplit.wave.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class SongsFragment extends Fragment {

    private RecyclerView songView;

    private ArrayList<Song> mSongItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    public static SongsFragment newInstance() {
        return new SongsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mSongItems = new ArrayList<Song>();
        songView = (RecyclerView) view.findViewById(R.id.song_list);

        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        songView.setLayoutManager(layoutManager);
        songView.setHasFixedSize(true);

        getSongList();

        mAdapter = new SongAdapter(getActivity().getApplicationContext(), mSongItems);
        songView.setAdapter(mAdapter);

        fastScroller.setRecyclerView(songView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        songView.setOnScrollListener(fastScroller.getOnScrollListener());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);
    }

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.YEAR};
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";


        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, cursor_cols, where, null, MediaStore.Audio.Media.TITLE);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int durationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                int thisDuration = musicCursor.getInt(durationColumn);
                mSongItems.add(new Song(thisId, thisTitle, thisArtist, thisAlbum, thisDuration));
            }
            while (musicCursor.moveToNext());
        }

    }
}