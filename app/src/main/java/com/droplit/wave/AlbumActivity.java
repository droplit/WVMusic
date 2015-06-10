package com.droplit.wave;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.droplit.wave.adapters.AlbumSongAdapter;
import com.droplit.wave.adapters.SongAdapter;
import com.droplit.wave.fragments.AlbumFragment;
import com.droplit.wave.models.Album;
import com.droplit.wave.models.Song;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;


public class AlbumActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "ALBUM_NAME";
    public static final String EXTRA_IMAGE = "ALBUM_ART";


    private String albumName;
    private String thisArtPath;

    private Album mAlbum;

    private RecyclerView songsView;

    private ArrayList<Song> mAlbumSongItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        final Context context = getApplicationContext();

        Intent intent = getIntent();
        albumName = intent.getStringExtra(EXTRA_NAME);
        thisArtPath = intent.getStringExtra(EXTRA_IMAGE);

        ImageView art = (ImageView) findViewById(R.id.backdrop);
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ImagePreview.class);
                i.putExtra("ART_PREVIEW", thisArtPath);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });




        mAlbumSongItems = new ArrayList<Song>();
        songsView = (RecyclerView) findViewById(R.id.album_song_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        songsView.setLayoutManager(layoutManager);
        songsView.setHasFixedSize(true);


        mAlbumSongItems.add(null);

        loadSongs();

        mAlbum = Globals.album;
        mAdapter = new AlbumSongAdapter(getApplicationContext(), mAlbumSongItems, mAlbum);
        songsView.setAdapter(mAdapter);

        Collections.sort(mAlbumSongItems, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                if(a == null || b == null) {
                    return 0;
                }
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout =
                (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Bitmap bitmap = BitmapFactory.decodeFile(mAlbum.getAlbumArt());
        if (thisArtPath != null) {
            DominantColorCalculator dcc = new DominantColorCalculator(bitmap);
            ColorScheme cs = dcc.getColorScheme();
            float[] hsv = new float[3];
            int color = cs.primaryAccent;
            Color.colorToHSV(color, hsv);
            hsv[2] *= 0.8f; // value component
            color = Color.HSVToColor(hsv);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                collapsingToolbar.setStatusBarScrimColor(color);
            }
            collapsingToolbar.setContentScrimColor(cs.primaryAccent);
            appBarLayout.setBackgroundColor(cs.primaryAccent);
            collapsingToolbar.setCollapsedTitleTextColor(cs.primaryText);
            collapsingToolbar.setTitle(albumName);
        } else {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(albumName);

            float[] hsv = new float[3];
            int darkColor = color;
            Color.colorToHSV(color, hsv);
            hsv[2] *= 0.8f; // value component
            color = Color.HSVToColor(hsv);
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

            collapsingToolbar.setTitle(albumName);
        }

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
                Log.v("ALBUM",
                        cursor.getString(cursor
                                .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM)));
            } while (cursor.moveToNext());
        }

        String[] column = { MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
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
            int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);

            //add songs to list
            do {
                Log.v("ALBUM",
                        cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                int thisDuration = cursor.getInt(durationColumn);
                mAlbumSongItems.add(new Song(thisId, thisTitle, thisArtist, null, thisDuration));

            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.album_overflow_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setType("text/plain");
                String shareBody = "I'm listening to the album, \"" + mAlbum.getTitle() + "\" by " + mAlbum.getArtist() + "\n\n#WV";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "WV Music");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(albumName);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(albumName.substring(0,1), color);

        Picasso.with(getApplicationContext())
                .load(Uri.parse("file://" + thisArtPath))
                .placeholder(drawable)
                .error(drawable)
                .into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album, menu);
        return true;
    }

}
