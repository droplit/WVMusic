package com.droplit.wave.ui.fragments;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;

import com.droplit.wave.R;
import com.droplit.wave.adapters.AlbumGridAdapter;
import com.droplit.wave.adapters.AlbumGridCardAdapter;
import com.droplit.wave.adapters.AlbumGridPaletteAdapter;
import com.droplit.wave.models.Album;

import java.util.ArrayList;

public class AlbumGridFragment extends Fragment {

    public static final String PREFS_NAME = "WVPrefs";


    private GridView albumView;

    private ArrayList<Album> mAlbumItems = new ArrayList<>();
    private AlbumGridPaletteAdapter pAdapter;
    private AlbumGridAdapter gAdapter;
    private AlbumGridCardAdapter cAdapter;


    private int albumCols = GridView.AUTO_FIT;
    private int albumViewType = 0;

    private SharedPreferences views;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mAlbumItems = new ArrayList<Album>();
        albumView = (GridView) view.findViewById(R.id.album_grid_list);

        getAlbumList();

        views = getActivity().getSharedPreferences(PREFS_NAME, 0);
        albumViewType = views.getInt("albumView", 0);
        albumCols = views.getInt("albumCols", GridView.AUTO_FIT);

        if(albumViewType == 1) {
            gAdapter = new AlbumGridAdapter(getActivity().getApplicationContext(), mAlbumItems);
            albumView.setAdapter(gAdapter);
        } else if(albumViewType == 2) {
            pAdapter = new AlbumGridPaletteAdapter(getActivity().getApplicationContext(), mAlbumItems);
            albumView.setAdapter(pAdapter);
        } else if(albumViewType == 3) {
            cAdapter = new AlbumGridCardAdapter(getActivity().getApplicationContext(), mAlbumItems);
            albumView.setAdapter(cAdapter);
        }
        if(albumViewType >= 1) {
            albumView.setNumColumns(albumCols);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_album_grid_fragment,menu);
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
        Cursor musicCursor = musicResolver.query(musicUri,cursor_cols,null,null,MediaStore.Audio.AlbumColumns.ALBUM);
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