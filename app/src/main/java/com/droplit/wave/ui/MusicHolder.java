package com.droplit.wave.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droplit.wave.R;
import com.droplit.wave.appwidgets.RecentWidgetService;

import java.lang.ref.WeakReference;

/**
 * Used to efficiently cache and recyle the {@link View}s used in the artist,
 * album, song, playlist, and genre adapters.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class MusicHolder {

    /**
     * This is the overlay ontop of the background artist, playlist, or genre
     * image
     */
    public WeakReference<RelativeLayout> mOverlay;

    /**
     * This is the background artist, playlist, or genre image
     */
    public WeakReference<ImageView> mBackground;

    /**
     * This is the artist or album image
     */
    public WeakReference<ImageView> mImage;

    /**
     * This is the first line displayed in the list or grid
     *
     * @see {@code #getView()} of a specific adapter for more detailed info
     */
    public WeakReference<TextView> mLineOne;

    /**
     * This is displayed on the right side of the first line in the list or grid
     *
     * @see {@code #getView()} of a specific adapter for more detailed info
     */
    public WeakReference<TextView> mLineOneRight;

    /**
     * This is the second line displayed in the list or grid
     *
     * @see {@code #getView()} of a specific adapter for more detailed info
     */
    public WeakReference<TextView> mLineTwo;

    /**
     * This is the third line displayed in the list or grid
     *
     * @see {@code #getView()} of a specific adapter for more detailed info
     */
    public WeakReference<TextView> mLineThree;

    /**
     * Constructor of <code>ViewHolder</code>
     *
     * @param context The {@link Context} to use.
     */
    public MusicHolder(final View view) {
        super();
        // Initialize mOverlay
        mOverlay = new WeakReference<RelativeLayout>(
                (RelativeLayout) view.findViewById(R.id.image_background));

        // Initialize mBackground
        mBackground = new WeakReference<ImageView>(
                (ImageView) view.findViewById(R.id.list_item_background));

        // Initialize mImage
        mImage = new WeakReference<ImageView>((ImageView) view.findViewById(R.id.image));

        // Initialize mLineOne
        mLineOne = new WeakReference<TextView>((TextView) view.findViewById(R.id.line_one));

        // Initialize mLineOneRight
        mLineOneRight = new WeakReference<TextView>(
                (TextView) view.findViewById(R.id.line_one_right));

        // Initialize mLineTwo
        mLineTwo = new WeakReference<TextView>((TextView) view.findViewById(R.id.line_two));

        // Initialize mLineThree
        mLineThree = new WeakReference<TextView>((TextView) view.findViewById(R.id.line_three));
    }

    /**
     * @param view The {@link View} used to initialize content
     */
    public final static class DataHolder {

        /**
         * This is the ID of the item being loaded in the adapter
         */
        public long mItemId;

        /**
         * This is the first line displayed in the list or grid
         *
         * @see {@code #getView()} of a specific adapter for more detailed info
         */
        public String mLineOne;

        /**
         * This is displayed on the right side of the first line in the list or grid
         *
         * @see {@code #getView()} of a specific adapter for more detailed info
         */
        public String mLineOneRight;

        /**
         * This is the second line displayed in the list or grid
         *
         * @see {@code #getView()} of a specific adapter for more detailed info
         */
        public String mLineTwo;

        /**
         * This is the third line displayed in the list or grid
         *
         * @see {@code #getView()} of a specific adapter for more detailed info
         */
        public String mLineThree;

        /**
         * This is the album art bitmap used in {@link RecentWidgetService}.
         */
        public Bitmap mImage;

        /**
         * Constructor of <code>DataHolder</code>
         */
        public DataHolder() {
            super();
        }

    }
}