package com.droplit.wave.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.droplit.wave.AlbumActivity;
import com.droplit.wave.ColorScheme;
import com.droplit.wave.DominantColorCalculator;
import com.droplit.wave.Globals;
import com.droplit.wave.MainActivity;
import com.droplit.wave.R;
import com.droplit.wave.models.Album;
import com.droplit.wave.util.ArtLoader;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;

public class AlbumGridAdapter extends CursorAdapter implements SectionIndexer{

    public AlbumGridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_album_grid, null);
        view.setTag(R.id.album_art, view.findViewById(R.id.album_art));
        view.setTag(R.id.album_artist, view.findViewById(R.id.album_artist));
        view.setTag(R.id.album_title, view.findViewById(R.id.album_title));

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title1 = (TextView) view.findViewById(R.id.album_title);
        TextView artist1 = (TextView) view.findViewById(R.id.album_artist);
        ImageView album1 = (ImageView) view.findViewById(R.id.album_art);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ARTIST));
        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ART));
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));
        StringBuilder titleBuild = new StringBuilder();
        titleBuild.append(title);
        if(titleBuild.length() > 35)
        {
            titleBuild.setLength(32);
            title = titleBuild.toString()+"...";
        }
        else
        {
            title = titleBuild.toString();
        }
        StringBuilder artistBuild = new StringBuilder();
        artistBuild.append(artist);
        if(artistBuild.length() > 35)
        {
            artistBuild.setLength(32);
            artist = artistBuild.toString()+"...";
        }
        else
        {
            artist = artistBuild.toString();
        }


        new ArtLoader(album1).execute(album);

        title1.setText(title);
        artist1.setText(artist);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(title);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(title.substring(0, 1).toUpperCase(), color);


        if(album != null) {
            //DominantColorCalculator dcc = new DominantColorCalculator(BitmapFactory.decodeFile(album));
            //ColorScheme cs = dcc.getColorScheme();
            //title1.setBackgroundColor(cs.primaryAccent);
            //artist1.setBackgroundColor(cs.primaryAccent);
            //title1.setTextColor(cs.primaryText);
            //artist1.setTextColor(cs.secondaryText);
        } else {
            album1.setImageDrawable(drawable);
            title1.setBackgroundColor(color);
            artist1.setBackgroundColor(color);
        }
    }



    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}