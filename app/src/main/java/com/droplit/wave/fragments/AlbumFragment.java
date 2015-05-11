package com.droplit.wave.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droplit.wave.R;
import com.droplit.wave.adapters.AlbumAdapter;
import com.droplit.wave.adapters.SongAdapter;
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Song;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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
        /*albumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //albumPicked(view);
                Toast.makeText(getActivity().getApplicationContext(),"Chose an album", Toast.LENGTH_SHORT).show();
            }
        });*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumView.setLayoutManager(layoutManager);
        albumView.setHasFixedSize(true);

        getAlbumList();

        mAdapter = new AlbumAdapter(getActivity().getApplicationContext(), mAlbumItems);
        albumView.setAdapter(mAdapter);

        Collections.sort(mAlbumItems, new Comparator<Album>() {
            public int compare(Album a, Album b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

    }

    public void getAlbumList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.TRACK};
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";

        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, cursor_cols, where, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int trackColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TRACK);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int albumYear = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.YEAR);
            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, idColumn);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getActivity().getApplicationContext().getContentResolver(), albumArtUri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 30, 30, true);

            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
                bitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                        R.drawable.default_artwork);
            } catch (IOException e) {

                e.printStackTrace();
            }
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(albumYear);
                mAlbumItems.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, bitmap));
            }

            while (musicCursor.moveToNext());
        }

    }
}