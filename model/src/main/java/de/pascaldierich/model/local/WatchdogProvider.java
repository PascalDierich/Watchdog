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

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.OBSERVABLES_PATH, CODE_OBSERVABLES);
        matcher.addURI(WatchdogContract.CONTENT_AUTHORITY, WatchdogContract.SITES_PATH, CODE_SITES);

        // TODO: 12.02.17 add Uri for Post-Tables

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mHelper = new WatchdogDBHelper(getContext(), 1);
        return true;
    }

    @NonNull
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
            // TODO: 12.02.17 add cases for Post-Tables 
            default :
                throw new UnsupportedOperationException("Not supported URI");
        }
    }

    @NonNull
    @Override
    public String getType(@NonNull Uri uri) {
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
                    throw new SQLException("failed to insert row");
                }
            }
            case CODE_SITES: {
                long _id = db.insert(WatchdogContract.Sites.TABLE_NAME, null, values);
                if (_id > 0) {
                    return WatchdogContract.Sites.buildSitesUriWithId(_id);
                } else {
                    throw new SQLException("failed to insert row");
                }
            }
            // TODO: 12.02.17 add cases for Post-Tables 
            default:
                throw new UnsupportedOperationException("Not supported URI");
        }
    }

    @IntRange(from = -1, to = 1)
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return -1;
    }

    @IntRange(from = -1, to = 1)
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return -1;
    }
}
