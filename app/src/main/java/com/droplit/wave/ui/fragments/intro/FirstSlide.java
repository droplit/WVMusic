package com.droplit.wave.ui.fragments.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.droplit.wave.R;

import com.eftimoff.androipathview.PathView;



public class FirstSlide extends Fragment {

    private PathView pathView;


    public static FirstSlide newInstance() {
        return new FirstSlide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pathView = (PathView) view.findViewById(R.id.pathView);
        pathView.useNaturalColors();

        pathView.setFillAfter(true);

        pathView.getPathAnimator()
                .delay(500)
                .duration(1000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

    }

}