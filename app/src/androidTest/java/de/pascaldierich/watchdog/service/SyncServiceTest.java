package de.pascaldierich.watchdog.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import de.pascaldierich.sync.WatchdogSyncService;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SyncServiceTest {

    @Rule
    public final ServiceTestRule mServiceTestRule = new ServiceTestRule();

    @Test
    public void test() throws TimeoutException {
        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(),
                        WatchdogSyncService.class);

        IBinder binder = mServiceTestRule.bindService(serviceIntent);

        WatchdogSyncService service =
                ((WatchdogSyncService.LocalBinder) binder).getService();


    }

}
