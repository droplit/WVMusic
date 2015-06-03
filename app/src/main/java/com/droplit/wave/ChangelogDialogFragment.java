package com.droplit.wave;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.droplit.wave.adapters.ChangeLogAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.gmariotti.changelibs.library.view.ChangeLogListView;

/**
 * Created by Julian on 6/2/2015.
 */
public class ChangelogDialogFragment extends DialogFragment {

        public ChangelogDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            ChangeLogListView chgList=(ChangeLogListView)layoutInflater.inflate(R.layout.dialog_changelog, null);

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.changelog)
                    .setView(chgList)
                    .setPositiveButton(R.string.about_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();

        }
}
