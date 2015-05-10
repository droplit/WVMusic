package com.droplit.wave;


/**
 * App-wide constants.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public final class Config {

    /**
     * My personal Last.fm API key, please use your own.
     */
    public static final String LASTFM_API_KEY = "6f4a9b86b922d5288a610555a4ce9c6c";
    /**
     * Used to distinguish album art from artist images
     */
    public static final String ALBUM_ART_SUFFIX = "album";
    /**
     * The ID of an artist, album, genre, or playlist passed to the profile
     * activity
     */
    public static final String ID = "id";
    /**
     * The name of an artist, album, genre, or playlist passed to the profile
     * activity
     */
    public static final String NAME = "name";
    /**
     * The name of an artist passed to the profile activity
     */
    public static final String ARTIST_NAME = "artist_name";
    /**
     * The year an album was released passed to the profile activity
     */
    public static final String ALBUM_YEAR = "album_year";
    /**
     * The MIME type passed to a the profile activity
     */
    public static final String MIME_TYPE = "mime_type";
    /**
     * Play from search intent
     */
    public static final String PLAY_FROM_SEARCH = "android.media.action.MEDIA_PLAY_FROM_SEARCH";

    /* This class is never initiated. */
    public Config() {
    }
}