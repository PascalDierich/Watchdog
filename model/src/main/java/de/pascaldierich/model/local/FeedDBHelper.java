package de.pascaldierich.model.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class FeedDBHelper extends SQLiteOpenHelper {

    public FeedDBHelper(Context context, int version) {
        super(context, FeedContract.DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    private String getObservablesStatement() {
        return "CREATE TABLE " + FeedContract.Observables.TABLE_NAME + " ("
                + FeedContract.Observables.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FeedContract.Observables.COLUMN_NAME + " TEXT NOT NULL,"
                + FeedContract.Observables.COLUMN_THUMBNAIL + " BLOB"
                + ");";
    }

    private String getSitesStatement() {
        return "";
    }

    private String getFavoritesStatement() {
        return "";
    }

    private String getNewsFeedStatement() {
        return "";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}