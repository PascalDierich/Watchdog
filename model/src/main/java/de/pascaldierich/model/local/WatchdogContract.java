package de.pascaldierich.model.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
abstract class WatchdogContract {
    static final String CONTENT_AUTHORITY = "de.pascaldierich.watchdogs";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String OBSERVABLES_PATH = "observables";
    static final String SITES_PATH = "sites";
    static final String NEWS_FEED_PATH = "newsfeed";
    static final String FAVORITES_PATH = "favorites";

    /**
     * Table 'Observables'
     * <p>
     * This table saves the DisplayName to use in App and create an
     * unique auto-incremented Key for each Observable.
     * An Image can be added too but isn't necessary.
     */
    static final class Observables implements BaseColumns {
        static final String TABLE_NAME = "Observables";

        static final Uri CONTENT_URI_OBSERVABLES =
                BASE_CONTENT_URI.buildUpon().appendPath(OBSERVABLES_PATH).build(); // TODO: 12.02.17 check Path

        // Columns Names
        static final String COLUMN_ID = "_ID"; // -> unique ID used by other tables for identification
        static final String COLUMN_NAME = "displayName"; // -> Name to show the User
        static final String COLUMN_THUMBNAIL = "thumbnail"; // -> possible to save image

        // Columns Id's
        static final int COLUMN_ID_ID = 0;
        static final int COLUMN_NAME_ID = 1;
        static final int COLUMN_THUMBNAIL_ID = 2;

        static Uri buildObservableUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_OBSERVABLES, id);
        }
    }

    /**
     * Table 'Sites'
     * <p>
     * This table will save the Site specific UserId's with the equivalent sites for the
     * API Services. The userId Column will hold the Observables ID.
     */
    static final class Sites implements BaseColumns {
        static final String TABLE_NAME = "Sites";

        static final Uri CONTENT_URI_SITES =
                BASE_CONTENT_URI.buildUpon().appendPath(SITES_PATH).build();

        // Columns Names
        static final String COLUMN_USER_ID = "userId"; // -> Observables Id for identification
        static final String COLUMN_SITE = "site"; // -> Name of the Site for checking defined in SupportedNetworks.class
        static final String COLUMN_KEY = "key"; // -> unique User-Id for equivalent social Network

        // Columns Id's
        static final int COLUMN_USER_ID_ID = 0;
        static final int COLUMN_SITE_ID = 1;
        static final int COLUMN_KEY_ID = 2;

        static Uri buildSitesUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_SITES, id);
        }
    }

    /**
     * Table 'NewsFeed'
     * <p>
     * This table holds the new posts which got downloaded.
     * The userId is equivalent to the Observables ID column.
     */
    static final class NewsFeed implements BaseColumns {
        static final String TABLE_NAME = "NewsFeed";

        static final Uri CONTENT_URI_NEWS_FEED =
                BASE_CONTENT_URI.buildUpon().appendPath(NEWS_FEED_PATH).build();

        // Columns Names
        static final String COLUMN_ID = "_ID"; // auto-generated Integer ID
        static final String COLUMN_USER_ID = "userId"; // Observables Id for identification
        static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl"; // Url for thumbnail
        static final String COLUMN_DESCRIPTION = "description"; // String, description of Post
        static final String COLUMN_TITLE = "title"; // String, title of Post
        static final String COLUMN_POST_ID = "postId"; // unique Post Id form site
        static final String COLUMN_SITE = "site"; // String of @interface SupportedNetworks
        static final String COLUMN_TIME_DOWNLOADED = "timeDownloaded"; // auto-generated Timestamp


        // Columns Id's
        static final int COLUMN_ID_ID = 0;
        static final int COLUMN_USER_ID_ID = 1;
        static final int COLUMN_THUMBNAIL_URL_ID = 2;
        static final int COLUMN_DESCRIPTION_ID = 3;
        static final int COLUMN_TITLE_ID = 4;
        static final int COLUMN_POST_ID_ID = 5;
        static final int COLUMN_SITE_ID = 6;
        static final int COLUMN_TIME_DOWNLOADED_ID = 7;

        static Uri buildNewsFeedUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_NEWS_FEED, id);
        }
    }

    /**
     * Table 'Favorites'
     * <p>
     * This table saves the favorites Posts locally.
     * The userId is equivalent to the Observables ID column.
     */
    static final class Favorites implements BaseColumns {
        static final String TABLE_NAME = "Favorites";

        static final Uri CONTENT_URI_FAVORITES =
                BASE_CONTENT_URI.buildUpon().appendPath(FAVORITES_PATH).build();

        // Columns Names
        static final String COLUMN_ID = "_ID"; // auto-generated Integer ID
        static final String COLUMN_USER_ID = "userId"; // Observables Id for identification
        static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl"; // Url for thumbnail
        static final String COLUMN_DESCRIPTION = "description"; // String, description of Post
        static final String COLUMN_TITLE = "title"; // String, title of Post
        static final String COLUMN_POST_ID = "postId"; // unique Post Id form site
        static final String COLUMN_SITE = "site"; // String of @interface SupportedNetworks
        static final String COLUMN_TIME_SAVED = "timeSaved"; // auto-generated Timestamp

        // Columns Id's
        static final int COLUMN_ID_ID = 0;
        static final int COLUMN_USER_ID_ID = 1;
        static final int COLUMN_THUMBNAIL_URL_ID = 2;
        static final int COLUMN_DESCRIPTION_ID = 3;
        static final int COLUMN_TITLE_ID = 4;
        static final int COLUMN_POST_ID_ID = 5;
        static final int COLUMN_SITE_ID = 6;
        static final int COLUMN_TIME_SAVED_ID = 7;

        static Uri buildNewsFeedUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_FAVORITES, id);
        }
    }
}
