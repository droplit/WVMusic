package com.droplit.wave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.droplit.wave.adapters.AlbumSongAdapter;
import com.droplit.wave.adapters.SongAdapter;
import com.droplit.wave.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class AlbumActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "ALBUM_NAME";

    private String albumName;
    private String thisArtPath;

    private RecyclerView songsView;

    private ArrayList<Song> mAlbumSongItems = new ArrayList<>();
    private ArrayList<String> mArtPaths = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);



        mAlbumSongItems = new ArrayList<Song>();
        songsView = (RecyclerView) findViewById(R.id.album_song_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        songsView.setLayoutManager(layoutManager);
        songsView.setHasFixedSize(true);

        Intent intent = getIntent();
        albumName = intent.getStringExtra(EXTRA_NAME);
        thisArtPath = intent.getStringExtra("ALBUM_ART");

        loadSongs();

        mAdapter = new AlbumSongAdapter(getApplicationContext(), mAlbumSongItems);
        songsView.setAdapter(mAdapter);

        songsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Collections.sort(mAlbumSongItems, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(albumName);

        loadBackdrop();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.album_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Plays from the album", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadSongs() {
        String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
                android.provider.MediaStore.Audio.Albums.ALBUM};

        Cursor cursor = managedQuery(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null,
                null, null);

        if (cursor.moveToFirst()) {


            do {
                Log.v("Vipul",
                        cursor.getString(cursor
                                .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM)));
            } while (cursor.moveToNext());
        }

        // I want to list down song in album Rolling Papers (Deluxe Version)

        String[] column = { MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.MIME_TYPE};

        String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";

        String whereVal[] = { albumName };

        String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

        cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                column, where, whereVal, orderBy);

        if (cursor.moveToFirst()) {
            //get columns
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int durationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);

            //add songs to list
            do {
                Log.v("Vipul",
                        cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                int thisDuration = cursor.getInt(durationColumn);
                mAlbumSongItems.add(new Song(thisId, thisTitle, null, null, thisDuration));

            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_tester:
                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                Log.d("ART", mArtPaths.toString());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Uri.parse("file://" + thisArtPath)).into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album, menu);
        return true;
    }

}
