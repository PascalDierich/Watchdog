package de.pascaldierich.model.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class WatchdogDBHelper extends SQLiteOpenHelper {

    // public for test
    public static final String DB_NAME = "watchdog.db";

    public WatchdogDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getObservablesStatement());
        db.execSQL(getSitesStatement());
    
        // TODO: 12.02.17 execute Post-Tables 
    }

    private String getObservablesStatement() {
        return "CREATE TABLE " + WatchdogContract.Observables.TABLE_NAME + " ("
                + WatchdogContract.Observables.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WatchdogContract.Observables.COLUMN_NAME + " TEXT NOT NULL,"
                + WatchdogContract.Observables.COLUMN_THUMBNAIL + " BLOB"
                + ");";
    }

    private String getSitesStatement() {
        return "CREATE TABLE " + WatchdogContract.Sites.TABLE_NAME + " ("
                + WatchdogContract.Sites.COLUMN_USER_ID + " INTEGER NOT NULL,"
                + WatchdogContract.Sites.COLUMN_SITE + " TEXT NOT NULL,"
                + WatchdogContract.Sites.COLUMN_KEY + " TEXT NOT NULL"
                + ");";
    }

    private String getFavoritesStatement() {
        return "";
    }

    private String getNewsFeedStatement() {
        return "CREATE TABLE " + WatchdogContract.NewsFeed.TABLE_NAME + " ("
                + WatchdogContract.NewsFeed.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WatchdogContract.NewsFeed.COLUMN_USER_ID + " INTEGER NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_THUMBNAIL_URL + " TEXT NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_TITLE + " TEXT NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_POST_ID + " TEXT NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_SITE + " TEXT NOT NULL,"
                + WatchdogContract.NewsFeed.COLUMN_TIME_DOWNLOADED + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}