package com.droplit.wave.ui;

import android.content.Context;
import android.util.AttributeSet;


import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;


public class SelectionTitleIndicatior extends SectionTitleIndicator<String> {

    public SelectionTitleIndicatior(Context context) {
        super(context);
    }

    public SelectionTitleIndicatior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionTitleIndicatior(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(String s) {
        setTitleText(s + "");

    }


}