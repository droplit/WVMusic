package com.droplit.wave;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.droplit.wave.adapters.ArtistSongAdapter;
import com.droplit.wave.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ArtistActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "ARTIST_NAME";

    private String artistName;

    private RecyclerView songsView;

    private ArrayList<Song> mArtistSongItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        mArtistSongItems = new ArrayList<Song>();
        songsView = (RecyclerView) findViewById(R.id.artist_view_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        songsView.setLayoutManager(layoutManager);
        songsView.setHasFixedSize(true);

        Intent intent = getIntent();
        artistName = intent.getStringExtra(EXTRA_NAME);

        loadSongs();

        mAdapter = new ArtistSongAdapter(getApplicationContext(), mArtistSongItems);
        songsView.setAdapter(mAdapter);

        songsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Collections.sort(mArtistSongItems, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(artistName);

        loadBackdrop();

    }

    private void loadSongs() {
        String[] columns = {android.provider.MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST};

        Cursor cursor = managedQuery(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null,
                null, null);

        if (cursor.moveToFirst()) {
            do {
                Log.v("Vipul",
                        cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
            } while (cursor.moveToNext());
        }

        // I want to list down song in album Rolling Papers (Deluxe Version)

        String[] column = {MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,};

        String where = MediaStore.Audio.Media.ARTIST + "=?";

        String whereVal[] = {artistName};

        String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

        cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                column, where, whereVal, orderBy);

        if (cursor.moveToFirst()) {
            //get columns
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int durationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                Log.v("Vipul",
                        cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                long thisId = cursor.getLong(idColumn);
                String thisArtist = cursor.getString(artistColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisAlbum = cursor.getString(albumColumn);
                int thisDuration = cursor.getInt(durationColumn);
                mArtistSongItems.add(new Song(thisId, thisTitle, thisArtist, thisAlbum, thisDuration));

            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //if(thisArtPath != null) {
        //    Glide.with(this).load(Uri.parse("file://" + thisArtPath)).into(imageView);
        //} else {
            int color = MaterialColorPalette.randomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(artistName.substring(0,1), color);
            imageView.setImageDrawable(drawable);
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artist, menu);
        return true;
    }
}
