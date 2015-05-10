package com.droplit.wave.adapters;


import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.droplit.wave.R;
import com.droplit.wave.model.Playlist;
import com.droplit.wave.ui.MusicHolder;
import com.droplit.wave.ui.MusicHolder.DataHolder;
import com.droplit.wave.ui.fragments.PlaylistFragment;

/**
 * This {@link ArrayAdapter} is used to display all of the playlists on a user's
 * device for {@link PlaylistFragment}.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    /**
     * Number of views (TextView)
     */
    private static final int VIEW_TYPE_COUNT = 1;

    /**
     * The resource Id of the layout to inflate
     */
    private final int mLayoutId;

    /**
     * Used to cache the playlist info
     */
    private DataHolder[] mData;

    /**
     * Constructor of <code>PlaylistAdapter</code>
     *
     * @param context  The {@link Context} to use.
     * @param layoutId The resource Id of the view to inflate.
     */
    public PlaylistAdapter(final Context context, final int layoutId) {
        super(context, 0);
        // Get the layout Id
        mLayoutId = layoutId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Recycle ViewHolder's items
        MusicHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mLayoutId, parent, false);
            holder = new MusicHolder(convertView);
            // Hide the second and third lines of text
            holder.mLineTwo.get().setVisibility(View.GONE);
            holder.mLineThree.get().setVisibility(View.GONE);
            // Make line one slightly larger
            holder.mLineOne.get().setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.text_size_large));
            convertView.setTag(holder);
        } else {
            holder = (MusicHolder) convertView.getTag();
        }

        // Retrieve the data holder
        final DataHolder dataHolder = mData[position];

        // Set each playlist name (line one)
        holder.mLineOne.get().setText(dataHolder.mLineOne);
        return convertView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /**
     * Method used to cache the data used to populate the list or grid. The idea
     * is to cache everything before {@code #getView(int, View, ViewGroup)} is
     * called.
     */
    public void buildCache() {
        mData = new DataHolder[getCount()];
        for (int i = 0; i < getCount(); i++) {
            // Build the artist
            final Playlist playlist = getItem(i);

            // Build the data holder
            mData[i] = new DataHolder();
            // Playlist Id
            mData[i].mItemId = playlist.mPlaylistId;
            // Playlist names (line one)
            mData[i].mLineOne = playlist.mPlaylistName;
        }
    }

    /**
     * Method that unloads and clears the items in the adapter
     */
    public void unload() {
        clear();
        mData = null;
    }

}