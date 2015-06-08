package com.droplit.wave;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;

public class ImagePreview extends AppCompatActivity {

    public static final String EXTRA_NAME = "ART_PREVIEW";

    private String thisArtPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_preview);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        thisArtPath = intent.getStringExtra(EXTRA_NAME);

        final ImageView imageView = (ImageView) findViewById(R.id.preview_art);
        Glide.with(this).load(Uri.parse("file://" + thisArtPath)).into(imageView);

    }
}
