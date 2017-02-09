package de.pascaldierich.model.network;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.pascaldierich.model.network.ConstantsApi.GOOGLE_BASE_URL;
import static de.pascaldierich.model.network.ConstantsApi.PLUS_COLLECTION;
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
        GOOGLE_BASE_URL
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
}