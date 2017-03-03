package de.pascaldierich.sync;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * see: https://developer.android.com/training/sync-adapters/creating-sync-adapter.html
 */
public class WatchdogSyncService extends Service {
    private static WatchdogSyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();
    
    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new WatchdogSyncAdapter(getApplicationContext(), true);
            }
        }
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
    
    
    public class LocalBinder extends Binder {
        public WatchdogSyncService getService() {
            return WatchdogSyncService.this;
        }
    }
}
