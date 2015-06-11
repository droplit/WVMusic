package com.droplit.wave.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.droplit.wave.AlbumActivity;
import com.droplit.wave.ColorScheme;
import com.droplit.wave.DominantColorCalculator;
import com.droplit.wave.Globals;
import com.droplit.wave.MaterialColorPalette;
import com.droplit.wave.R;
import com.droplit.wave.models.Album;
import com.droplit.wave.util.ArtLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumGridAdapter extends BaseAdapter {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;
    private ArrayList<String> artPaths = new ArrayList<>();

    private DisplayImageOptions options;


    public AlbumGridAdapter(Context c, ArrayList<Album> contents) {
        mContext = c;
        this.albums = contents;
        albumInf = LayoutInflater.from(c);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(mContext);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());


    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        //map to song layout
        if (view == null) {
            view = albumInf.inflate(R.layout.list_item_album_grid, parent, false);
        }
        //get title and artist views
        final TextView albumView = (TextView) view.findViewById(R.id.album_title);
        final TextView artistView = (TextView) view.findViewById(R.id.album_artist);
        final ImageView coverAlbum = (ImageView) view.findViewById(R.id.album_art);
        //get song using position
        currAlbum = albums.get(position);
        //albums.get(pos) = currAlbum.setTag(pos);
        //get title and artist strings
        albumView.setText(currAlbum.getTitle());
        artistView.setText(currAlbum.getArtist() + " | " + currAlbum.getNumSongs() + numSongs());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(currAlbum.getTitle());
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(currAlbum.getTitle().substring(0, 1).toUpperCase(), color);
        //new ArtLoader(coverAlbum).execute(currAlbum.getAlbumArt());

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if(currAlbum.getAlbumArt() != null) {
            ImageLoader.getInstance()
                    .displayImage("file://" + currAlbum.getAlbumArt(), coverAlbum, options, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                            Palette.generateAsync(loadedImage, new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette p) {
                                    // Here's your generated palette
                                    // Gets the RGB packed int -> same as palette.getVibrantColor(defaultColor);
                                    Palette.Swatch swatch = p.getVibrantSwatch();

                                    int rgbColor = 0;
                                    int titleTextColor = 0;
                                    int bodyTextColor = 0;

                                    if (swatch != null) {
                                        rgbColor = swatch.getRgb();
                                        // Gets an appropriate title text color
                                        titleTextColor = swatch.getTitleTextColor();
                                        // Gets an appropriate body text color
                                        bodyTextColor = swatch.getBodyTextColor();
                                    } else {

                                        swatch = p.getDarkVibrantSwatch();

                                        if(swatch != null) {
                                            rgbColor = swatch.getRgb();

                                            // Gets an appropriate title text color
                                            titleTextColor = swatch.getTitleTextColor();
                                            // Gets an appropriate body text color
                                            bodyTextColor = swatch.getBodyTextColor();
                                        } else {
                                            rgbColor = Color.DKGRAY;
                                            // Gets an appropriate title text color
                                            titleTextColor = Color.LTGRAY;
                                            // Gets an appropriate body text color
                                            bodyTextColor = Color.GRAY;
                                        }
                                    }
                                    albumView.setBackgroundColor(rgbColor);
                                    artistView.setBackgroundColor(rgbColor);
                                    albumView.setTextColor(titleTextColor);
                                    artistView.setTextColor(bodyTextColor);
                                }
                            });

                            /*Palette p  = Palette.generate(BitmapFactory.decodeFile(currAlbum.getAlbumArt()));

                            // Gets the RGB packed int -> same as palette.getVibrantColor(defaultColor);
                            Palette.Swatch swatch = p.getVibrantSwatch();

                            if (swatch != null) {
                                // Gets an appropriate title text color
                                int titleTextColor = swatch.getTitleTextColor();
                                // Gets an appropriate body text color
                                int bodyTextColor = swatch.getBodyTextColor();
                                albumView.setBackgroundColor(swatch.getRgb());
                                artistView.setBackgroundColor(swatch.getRgb());
                                albumView.setTextColor(titleTextColor);
                                artistView.setTextColor(bodyTextColor);
                            }*/
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {

                        }
                    });



        } else {
            coverAlbum.setImageDrawable(drawable);
            albumView.setBackgroundColor(color);
            artistView.setBackgroundColor(color);
        }


        //set position as tag
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AlbumActivity.class);
                i.putExtra("ALBUM_NAME", albums.get(position).getTitle());
                Globals.album = albums.get(position);
                //int position= (Integer)v.getTag();
                i.putExtra("ALBUM_ART", albums.get(position).getAlbumArt());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                Toast.makeText(mContext,albums.get(position).getTitle(),Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private String numSongs() {
        if (currAlbum.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }

}