package de.pascaldierich.model.local;

import android.content.ContentValues;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

@RunWith(AndroidJUnit4.class)
public class ProviderTest extends ProviderTestCase2<WatchdogProvider> {
    WatchdogDBHelper database;

    public ProviderTest() {
        super(WatchdogProvider.class, WatchdogContract.CONTENT_AUTHORITY);
    }

    @Override
    public MockContentResolver getMockContentResolver() {
        return new MockContentResolver();
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        getTargetContext().deleteDatabase(WatchdogDBHelper.DB_NAME);
        database = new WatchdogDBHelper(getTargetContext(), 1);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void CHECK_INSERT_OBSERVABLES() {
        Uri uri = WatchdogContract.Observables.CONTENT_URI_OBSERVABLES;

        for (ContentValues value : Utilitys.createObservablesValues()) {
            Uri returnUri = getMockContentResolver().insert(uri, value);

            assertTrue("returnUri is null", returnUri != null);
            assertTrue("returnUri = " + returnUri,
                    returnUri.toString().contains(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES.toString()));
        }

    }

}
