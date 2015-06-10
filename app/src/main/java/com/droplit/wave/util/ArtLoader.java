package com.droplit.wave.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.droplit.wave.R;

import java.lang.ref.WeakReference;

/**
 * Created by Julian on 6/10/2015.
 */
public class ArtLoader extends AsyncTask<Object, String, Bitmap> {

    private WeakReference<ImageView> mReference;
    private View view;
    private ImageView imageView;
    private Bitmap bitmap = null;
    public static BitmapDrawable drawable = null;
    private Context context;
    private Cursor cursor;

    public ArtLoader(ImageView imageView) {
        this.imageView = imageView;
        mReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Object... parameters) {

        // Get the passed arguments here

        String uri = (String) parameters[0];

        // Create bitmap from passed in Uri here
        // ...
        bitmap = BitmapFactory.decodeFile(uri);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mReference != null) {
            if (bitmap != null) {
                ImageView view = mReference.get();
                // note that this could still return null if the view or the reference has been
                // garbage collected which it could be since it is a weak reference, so you should
                // always check the status in this case.

                //do what you want with the image view.
                view.setImageBitmap(bitmap);
            }
        }
    }
}