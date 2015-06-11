package com.droplit.wave.fragments;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.droplit.wave.R;

/**
 * Created by Julian on 5/10/2015.
 */
public class QueueFragment {

    public void queueDialog(Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.app_name)
                .items(R.array.songs)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.d("TAG", "Item selected");
                    }
                })
                .show();
    }
}
