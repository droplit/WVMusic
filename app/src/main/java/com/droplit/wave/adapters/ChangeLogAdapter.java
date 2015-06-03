package com.droplit.wave.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.droplit.wave.R;

import java.util.ArrayList;

public class ChangeLogAdapter extends BaseAdapter {

    private ArrayList<String> logs;
    private LayoutInflater logInf;
    private String currLog;
    private final Context mContext;

    public ChangeLogAdapter(Context c, ArrayList<String> contents) {
        mContext = c;
        this.logs = contents;
        logInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to log layout
        final FrameLayout cardView = (FrameLayout) logInf.inflate
                (R.layout.list_item_log, parent, false);
        //get content view
        TextView contentView = (TextView) cardView.findViewById(R.id.log_contents);
        //get log using position
        currLog = logs.get(position);
        //get content string
        Spanned result = Html.fromHtml(currLog);
        contentView.setText(result);

        return cardView;
    }

}