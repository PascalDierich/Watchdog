package de.pascaldierich.model.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class WatchdogProvider extends ContentProvider {

    private WatchdogDBHelper mHelper;

    private static UriMatcher sMatcher = buildUriMatcher();

    private static final int CODE_OBSERVABLES = 100;
    private static final int CODE_SITES = 200;
    private static final int CODE_NEWS_FEED = 300;
    private static final int CODE_FAVORITES = 400;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.OBSERVABLES_PATH, CODE_OBSERVABLES);
        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.SITES_PATH, CODE_SITES);
        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.NEWS_FEED_PATH, CODE_NEWS_FEED);
        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.FAVORITES_PATH, CODE_FAVORITES);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mHelper = new WatchdogDBHelper(getContext(), 1);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sMatcher.match(uri)) {
            case CODE_OBSERVABLES: {
                return mHelper.getReadableDatabase().query(
                        WatchdogContract.Observables.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            case CODE_SITES: {
                return mHelper.getReadableDatabase().query(
                        WatchdogContract.Sites.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            case CODE_FAVORITES: {
                return mHelper.getReadableDatabase().query(
                        WatchdogContract.Favorites.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            case CODE_NEWS_FEED: {
                return mHelper.getReadableDatabase().query(
                        WatchdogContract.NewsFeed.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }
            default :
                throw new UnsupportedOperationException("Not supported URI: " + uri);
        }
    }

    @NonNull
    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: 21.02.17 define getType method for contentProvider
        return null;
    }

    @NonNull
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case CODE_OBSERVABLES: {
                long _id = db.insert(WatchdogContract.Observables.TABLE_NAME, null, values);
                if (_id > 0) {
                    return WatchdogContract.Observables.buildObservableUriWithId(_id);
                } else {
                    throw new SQLException("failed to insert row: " + CODE_OBSERVABLES);
                }
            }
            case CODE_SITES: {
                long _id = db.insert(WatchdogContract.Sites.TABLE_NAME, null, values);
                if (_id > 0) {
                    return WatchdogContract.Sites.buildSitesUriWithId(_id);
                } else {
                    throw new SQLException("failed to insert row: " + CODE_SITES);
                }
            }
            case CODE_FAVORITES: {
                long _id = db.insert(WatchdogContract.Favorites.TABLE_NAME, null, values);
                if (_id > 0) {
                    return WatchdogContract.Favorites.buildFavoritesUriWithId(_id);
                } else {
                    throw new SQLException("failed to insert row: " + CODE_FAVORITES);
                }
            }
            case CODE_NEWS_FEED: {
                long _id = db.insert(WatchdogContract.NewsFeed.TABLE_NAME, null, values);
                if (_id > 0) {
                    return WatchdogContract.NewsFeed.buildNewsFeedUriWithId(_id);
                } else {
                    throw new SQLException("failed to insert row: " + CODE_NEWS_FEED);
                }
            }
            default:
                throw new UnsupportedOperationException("Not supported URI: " + uri);
        }
    }

    @IntRange(from = -1, to = 1)
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // TODO: 21.02.17 define delete method for contentProvider
        return -1;
    }

    @IntRange(from = -1, to = 1)
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: 21.02.17 define update method for contentProvider
        return -1;
    }
}
