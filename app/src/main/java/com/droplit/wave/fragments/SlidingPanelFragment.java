package com.droplit.wave.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.droplit.wave.R;
import com.droplit.wave.adapters.RecyclerViewAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by Julian on 4/18/2015.
 */

public class SlidingPanelFragment extends Fragment {

    private static final String TAG = "SlidingPanel";

    private SlidingUpPanelLayout mLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sliding_panel, container, false);


        //setSupportActionBar((Toolbar)root.findViewById(R.id.main_toolbar));


        mLayout = (SlidingUpPanelLayout) root.findViewById(R.id.sliding_layout);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");
                Toast.makeText(getActivity(),"This is when an animation would occur",Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"And a change in the layout.",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });

        TextView t = (TextView) root.findViewById(R.id.name);
        t.setText(Html.fromHtml(getString(R.string.song_info)));
        Button f = (Button) root.findViewById(R.id.follow);
        f.setText(R.string.play);
        f.setMovementMethod(LinkMovementMethod.getInstance());
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Playing",Toast.LENGTH_SHORT).show();
            }
        });
    return root;
    }


}