package com.droplit.wave;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.droplit.wave.adapters.ArtistAlbumsAdapter;
import com.droplit.wave.adapters.ArtistSongAdapter;
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Song;

import java.util.ArrayList;


import static android.graphics.Color.TRANSPARENT;


public class ArtistActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "ARTIST_NAME";
    public static final String EXTRA_ID = "ARTIST_ID";

    private String artistName;
    private Long artistId;

    private ArtistAlbumsAdapter cAdapter;

    private RecyclerView songsView;
    private RecyclerView albumsView;

    private ArrayList<Song> mArtistSongItems = new ArrayList<>();
    private ArrayList<Album> mArtistAlbumItems = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter aAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        mArtistSongItems = new ArrayList<Song>();
        mArtistAlbumItems = new ArrayList<Album>();
        songsView = (RecyclerView) findViewById(R.id.artist_view_list);
        albumsView = (RecyclerView) findViewById(R.id.artist_album_view_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        songsView.setLayoutManager(layoutManager);
        songsView.setHasFixedSize(true);

        LinearLayoutManager gLayoutManager= new LinearLayoutManager(getApplicationContext());
        gLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        albumsView.setLayoutManager(gLayoutManager);
        albumsView.setHasFixedSize(true);

        Intent intent = getIntent();
        artistName = intent.getStringExtra(EXTRA_NAME);
        artistId = intent.getLongExtra(EXTRA_ID, 0);


        loadSongs();
        loadAlbums();

        mAdapter = new ArtistSongAdapter(getApplicationContext(), mArtistSongItems);
        songsView.setAdapter(mAdapter);

        aAdapter = new ArtistAlbumsAdapter(getApplicationContext(), mArtistAlbumItems);
        albumsView.setAdapter(aAdapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout =
                (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar.setExpandedTitleColor(TRANSPARENT);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int darkColor = generator.getColor(artistName);

        float[] hsv = new float[3];
        int color = darkColor;
        Color.colorToHSV(darkColor, hsv);
        hsv[2] *= 0.8f; // value component
        darkColor = Color.HSVToColor(hsv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            collapsingToolbar.setStatusBarScrimColor(darkColor);
        }
        collapsingToolbar.setContentScrimColor(color);
        appBarLayout.setBackgroundColor(color);

        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        if ((r*0.299 + g*0.587 + b*0.114) > 186) {
            collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
        } else {
            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        }

        collapsingToolbar.setTitle(artistName);

        loadBackdrop();
    }



    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private void loadSongs() {
        // Create the Cursor
        Cursor mCursor = makeArtistSongCursor(getApplicationContext(), artistId);
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                // Copy the song Id
                Long id = mCursor.getLong(0);

                // Copy the song name
                String songName = mCursor.getString(1);

                // Copy the artist name
                String artist = mCursor.getString(2);

                // Copy the album name
                String album = mCursor.getString(3);

                //Copy the song duration
                int duration = mCursor.getInt(4);


                // Create a new song
                Song song = new Song(id, songName, artist, album, duration);

                // Add everything up
                mArtistSongItems.add(song);
            } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    public static final Cursor makeArtistSongCursor(final Context context, final Long artistId) {
        // Match the songs up with the artist
        final StringBuilder selection = new StringBuilder();
        selection.append(MediaStore.Audio.AudioColumns.IS_MUSIC + "=1");
        selection.append(" AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''");
        selection.append(" AND " + MediaStore.Audio.AudioColumns.ARTIST_ID + "=" + artistId);
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        MediaStore.Audio.AudioColumns.TITLE,
                        /* 2 */
                        MediaStore.Audio.AudioColumns.ARTIST,
                        /* 3 */
                        MediaStore.Audio.AudioColumns.ALBUM,
                        /* 4 */
                        MediaStore.Audio.AudioColumns.DURATION
                }, selection.toString(), null, MediaStore.Audio.AudioColumns.ALBUM);
    }

    private void loadAlbums() {
        Cursor mCursor = makeArtistAlbumCursor(getApplicationContext(), artistId);
            // Gather the dataS
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    // Copy the album id
                    Long id = mCursor.getLong(0);

                    // Copy the album name
                    String albumName = mCursor.getString(1);

                    // Copy the artist name
                    String artist = mCursor.getString(2);

                    // Copy the number of songs
                    int songCount = mCursor.getInt(3);

                    // Copy the release year
                    String art = mCursor.getString(4);


                    // Create a new album
                    final Album album = new Album(id, albumName, artist, songCount, null, art);

                    // Add everything up
                    mArtistAlbumItems.add(album);
                } while (mCursor.moveToNext());
            }
            // Close the cursor
            if (mCursor != null) {
                mCursor.close();
                mCursor = null;
            }
        }

    public static final Cursor makeArtistAlbumCursor(final Context context, final Long artistId) {
        return context.getContentResolver().query(
                MediaStore.Audio.Artists.Albums.getContentUri("external", artistId), new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        MediaStore.Audio.AlbumColumns.ALBUM,
                        /* 2 */
                        MediaStore.Audio.AlbumColumns.ARTIST,
                        /* 3 */
                        MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS,
                        /* 4 */
                        MediaStore.Audio.AlbumColumns.ALBUM_ART
                }, null, null, MediaStore.Audio.AlbumColumns.ALBUM);
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
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(artistName);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(artistName.substring(0,1).toUpperCase(), color);
            imageView.setImageDrawable(drawable);
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artist, menu);
        return true;
    }
}
