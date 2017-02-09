package de.pascaldierich.model.network;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.pascaldierich.model.network.ConstantsApi.GOOGLE_BASE_URL;
import static de.pascaldierich.model.network.ConstantsApi.PLUS_COLLECTION;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_EVENT_TYPE;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_PART_ID;
import static de.pascaldierich.model.network.ConstantsApi.YOUTUBE_PART_VIDEOS;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Api Constants for Retrofit
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PLUS_COLLECTION,
        YOUTUBE_PART_VIDEOS,
        YOUTUBE_EVENT_TYPE,
        YOUTUBE_PART_ID,
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
    public static final String YOUTUBE_PART_VIDEOS = "snippet";
    public static final String YOUTUBE_EVENT_TYPE = "completed";
    public static final String YOUTUBE_PART_ID = "id";

    /**
     * Base URL's
     */
    public static final String GOOGLE_BASE_URL = "https://www.googleapis.com";
}