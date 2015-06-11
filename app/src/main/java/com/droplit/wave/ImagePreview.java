package com.droplit.wave;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ImagePreview extends AppCompatActivity {

    public static final String EXTRA_NAME = "ART_PREVIEW";

    private String thisArtPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_preview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }


        Intent intent = getIntent();
        thisArtPath = intent.getStringExtra(EXTRA_NAME);

        final ImageView imageView = (ImageView) findViewById(R.id.preview_art);
        Picasso.with(getApplicationContext())
                .load(Uri.parse("file://" + thisArtPath))
                .into(imageView);

    }
}
