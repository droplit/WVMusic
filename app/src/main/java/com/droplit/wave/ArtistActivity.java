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
