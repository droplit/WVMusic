package com.droplit.wave.utils;


import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.provider.MediaStore;

import com.droplit.wave.Config;
import com.droplit.wave.R;
import com.droplit.wave.model.Album;
import com.droplit.wave.ui.activities.AudioPlayerActivity;
import com.droplit.wave.ui.activities.HomeActivity;
import com.droplit.wave.ui.activities.ProfileActivity;
import com.droplit.wave.ui.activities.SearchActivity;
import com.droplit.wave.ui.activities.SettingsActivity;
import com.droplit.wave.AppMsg;

/**
 * Various navigation helpers.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public final class NavUtils {

    /**
     * Opens the profile of an artist.
     *
     * @param context    The {@link Activity} to use.
     * @param artistName The name of the artist
     */
    public static void openArtistProfile(final Activity context,
                                         final String artistName) {

        // Create a new bundle to transfer the artist info
        final Bundle bundle = new Bundle();
        bundle.putLong(Config.ID, MusicUtils.getIdForArtist(context, artistName));
        bundle.putString(Config.MIME_TYPE, MediaStore.Audio.Artists.CONTENT_TYPE);
        bundle.putString(Config.ARTIST_NAME, artistName);

        // Create the intent to launch the profile activity
        final Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * Opens the profile of an album.
     *
     * @param context    The {@link Activity} to use.
     * @param albumName  The name of the album
     * @param artistName The name of the album artist
     * @param albumId    The id of the album
     */
    public static void openAlbumProfile(final Activity context,
                                        final String albumName, final String artistName, final long albumId) {

        // Create a new bundle to transfer the album info
        final Bundle bundle = new Bundle();
        bundle.putString(Config.ALBUM_YEAR, MusicUtils.getReleaseDateForAlbum(context, albumId));
        bundle.putString(Config.ARTIST_NAME, artistName);
        bundle.putString(Config.MIME_TYPE, MediaStore.Audio.Albums.CONTENT_TYPE);
        bundle.putLong(Config.ID, albumId);
        bundle.putString(Config.NAME, albumName);

        // Create the intent to launch the profile activity
        final Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * Opens the sound effects panel or DSP manager in CM
     *
     * @param context The {@link Activity} to use.
     */
    public static void openEffectsPanel(final Activity context) {
        try {
            final Intent effects = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
            effects.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, MusicUtils.getAudioSessionId());
            context.startActivity(effects);
        } catch (final ActivityNotFoundException notFound) {
            AppMsg.makeText(context, context.getString(R.string.no_effects_for_you),
                    AppMsg.STYLE_ALERT);
        }
    }

    /**
     * Opens to {@link SettingsActivity}.
     *
     * @param activity The {@link Activity} to use.
     */
    public static void openSettings(final Activity activity) {
        final Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Opens to {@link AudioPlayerActivity}.
     *
     * @param activity The {@link Activity} to use.
     */
    public static void openAudioPlayer(final Activity activity) {
        final Intent intent = new Intent(activity, AudioPlayerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Opens to {@link SearchActivity}.
     *
     * @param activity The {@link Activity} to use.
     * @param query    The search query.
     */
    public static void openSearch(final Activity activity, final String query) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(SearchManager.QUERY, query);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Opens to {@link HomeActivity}.
     *
     * @param activity The {@link Activity} to use.
     */
    public static void goHome(final Activity activity) {
        final Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}