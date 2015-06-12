package com.droplit.wave;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridActivity extends AppCompatActivity {

    int idIndex;
    int dataIndex;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        fetchDeviceImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchDeviceImages(){
        String[] projection = {MediaStore.Images.Media._ID};
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, "", null, null);
        idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        setupGrid();
    }

    private void setupGrid(){
        GridView gv = (GridView) findViewById(R.id.gridView);
        ImagesAdapter adapter = new ImagesAdapter(this);
        gv.setAdapter(adapter);

    }

    public class ImagesAdapter extends BaseAdapter {

        Context context;
        int imageId;

        public ImagesAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_album_grid_palette, null);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.album_art);

            cursor.moveToPosition(position);
            imageId = cursor.getInt(idIndex);
            Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), imageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            thumbnail.setImageBitmap(bm);

            return view;
        }
    }
}
