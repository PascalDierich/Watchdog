package de.pascaldierich.model.network;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import de.pascaldierich.model.BuildConfig;

import static de.pascaldierich.model.network.ConstantsApi.GOOGLE_BASE_URL;
import static de.pascaldierich.model.network.ConstantsApi.PLUS_API_KEY;
import static de.pascaldierich.model.network.ConstantsApi.PLUS_COLLECTION;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_API_KEY;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_CHANNEL_PART;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_EVENT_TYPE;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_SEARCH_PART;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Api Constants for Retrofit
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PLUS_COLLECTION,
        YOUTUBE_SEARCH_PART,
        YOUTUBE_EVENT_TYPE,
        YOUTUBE_CHANNEL_PART,
        GOOGLE_BASE_URL,
        PLUS_API_KEY,
        YOUTUBE_API_KEY
})
public @interface ConstantsApi {
    /**
     * Google+ Api String Constants
     */
    public static final String PLUS_COLLECTION = "public";

    /**
     * YouTube Api String Constants
     */
    public static final String YOUTUBE_SEARCH_PART = "snippet";
    public static final String YOUTUBE_EVENT_TYPE = "completed";
    public static final String YOUTUBE_CHANNEL_PART = "id";

    /**
     * Base URL's
     */
    public static final String GOOGLE_BASE_URL = "https://www.googleapis.com";

    /**
     * Api Keys
     */
    public static final String PLUS_API_KEY = BuildConfig.GooglePlus_Api_Key;
    public static final String YOUTUBE_API_KEY  = BuildConfig.YouTube_Api_Key;
}