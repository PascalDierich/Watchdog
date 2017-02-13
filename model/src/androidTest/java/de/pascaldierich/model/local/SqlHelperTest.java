package de.pascaldierich.model.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

@RunWith(AndroidJUnit4.class)
public class SqlHelperTest {

    private WatchdogDBHelper database;
    private Cursor cursor;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(WatchdogDBHelper.DB_NAME);
        database = new WatchdogDBHelper(getTargetContext(), 1);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void CHECK_TABLE_OBSERVABLES_READ_WRITE() throws Exception {
        CHECK_TABLE_OBSERVABLE_INSERT();
        CHECK_TABLE_OBSERVABLE_QUERY();
    }

    private void CHECK_TABLE_OBSERVABLE_INSERT() throws Exception {
        SQLiteDatabase db = database.getWritableDatabase();

        for (ContentValues values : Utilitys.createObservablesValues()) {
            long _id = db.insert("Observables", null, values);
        }
    }

    private void CHECK_TABLE_OBSERVABLE_QUERY() throws Exception {
        SQLiteDatabase db = database.getReadableDatabase();

        cursor = db.query("Observables", null, null, null, null, null, null);

        cursor.moveToFirst();

        do {
            String name = cursor.getString(1); // displayName
            int id = cursor.getInt(0); // id
        } while (cursor.moveToNext());

        cursor.close();
    }

    @Test
    public void CHECK_TABLE_SITES_READ_WRITE() throws Exception {
        CHECK_TABLE_SITES_WRITE();
        CHECK_TABLE_SITES_QUERY();
    }

    private void CHECK_TABLE_SITES_WRITE() {
        SQLiteDatabase db = database.getWritableDatabase();

        for (ContentValues values : Utilitys.createSitesValues()) {
            long _id = db.insert("Sites", null, values);
        }
    }

    private void CHECK_TABLE_SITES_QUERY() {
        SQLiteDatabase db = database.getReadableDatabase();

        cursor = db.query("Sites", null, null, null, null, null, null);

        cursor.moveToFirst();

        do {
            int _id = cursor.getInt(0);
            String site = cursor.getString(1);
            String key = cursor.getString(2);
        } while (cursor.moveToNext());

        cursor.close();
    }

}
