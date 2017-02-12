package de.pascaldierich.model.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
abstract class FeedContract {
    static final String DB_NAME = "watchdog.db";

    private static final String CONTENT_AUTHORITY = "de.pascaldierich.watchdogs";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final class Observables implements BaseColumns {
        static final Uri CONTENT_URI_OBSERVABLES =
                BASE_CONTENT_URI.buildUpon().appendPath("observables").build(); // TODO: 12.02.17 check Path


        static final String TABLE_NAME = "Observables";

        // Columns Names
        static final String COLUMN_ID = "id";
        static final String COLUMN_NAME = "displayName";
        static final String COLUMN_THUMBNAIL = "thumbnail";

        // Columns Id's
        static int COLUMN_ID_ID = 0;
        static int COLUMN_NAME_ID = 1;
        static int COLUMN_THUMBNAIL_ID = 2;

        public static Uri buildObservableUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_OBSERVABLES, id);
        }
    }

    static final class Sites implements BaseColumns {
        static final Uri CONTENT_URI_SITES =
                BASE_CONTENT_URI.buildUpon().appendPath("sites").build();

        static final String TABLE_NAME = "Sites";

        // Columns Names
        static final String COLUMN_ID = "id";
        static final String COLUMN_SITE = "site";
        static final String COLUMN_KEY = "key";

        // Columns Id's
    }



}
