package de.pascaldierich.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {
    private WatchdogAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new WatchdogAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
