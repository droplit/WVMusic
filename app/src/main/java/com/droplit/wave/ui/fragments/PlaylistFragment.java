package com.droplit.wave.ui.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.droplit.wave.DividerItemDecoration;
import com.droplit.wave.R;
import com.droplit.wave.adapters.ArtistAdapter;
import com.droplit.wave.adapters.PlaylistAdapter;
import com.droplit.wave.models.Artist;
import com.droplit.wave.models.Playlist;

import java.util.ArrayList;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


public class PlaylistFragment extends Fragment {

    private RecyclerView playlistView;

    private ArrayList<Playlist> mPlaylistItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playlist, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mPlaylistItems = new ArrayList<Playlist>();
        playlistView = (RecyclerView) view.findViewById(R.id.playlist_list);


        playlistView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        playlistView.setLayoutManager(layoutManager);
        playlistView.setHasFixedSize(true);

        getPlaylists();

        mAdapter = new PlaylistAdapter(getActivity().getApplicationContext(), mPlaylistItems);
        playlistView.setAdapter(mAdapter);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    public void getPlaylists() {
        // Add the deafult playlits to the adapter
        //makeDefaultPlaylists();

        // Create the Cursor
        Cursor mCursor = makePlaylistCursor(getActivity().getApplicationContext());
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                // Copy the playlist id
                final String id = mCursor.getString(0);

                // Copy the playlist name
                final String name = mCursor.getString(1);

                // Create a new playlist
                final Playlist playlist = new Playlist(id, name);

                // Add everything up
                mPlaylistItems.add(playlist);
            } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    public static final Cursor makePlaylistCursor(final Context context) {
        return context.getContentResolver().query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        MediaStore.Audio.PlaylistsColumns.NAME
                }, null, null, MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER);
    }
}