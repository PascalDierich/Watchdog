package de.pascaldierich.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.pascaldierich.domain.interactors.service.Search;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.watchdog.R;

public class WatchdogSyncAdapter extends AbstractThreadedSyncAdapter {
    
    static final int SYNC_INTERVAL = 60 * 120;
    static final int FLEX_TIME = SYNC_INTERVAL / 2;
    
    static final int RANGE = 5;
    
    public WatchdogSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }
    
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        WeakReference<Search> wInteractor = new WeakReference<Search>(new Search(
                "2016-01-08T12:14:17Z", // time // TODO: 08.03.17 replace with getTime()
                getContext(),
                RANGE
        ));
        
        try {
            wInteractor.get().execute();
            saveTime();
        } catch (ModelException modelE) {
            // TODO: 08.03.17 report ExceptionCode to Firebase
        }
    }
    
    
    
    /*
        private Methods
     */
    
    /**
     * return the last time of sync.
     * Used for Api-Request inside Model
     * <p/>
     *
     * @return date, String: the date saved in SharedPrefenreces. getDefaultTime() if error
     */
    private String getTime() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getContext().getString(R.string.sharedPref_fileName), Context.MODE_PRIVATE);
        
        return sharedPref.getString(getContext().getString(R.string.syncService_sharedPref_timeKey), getDefaultTime());
    }
    
    /**
     * This method returns current date - 1 day.
     * Is used as default value for SharedPreferences
     * <p/>
     *
     * @return date, String: the date of yesterday
     */
    private String getDefaultTime() {
        return new SimpleDateFormat(getContext().getString(R.string.rfc3339_format), Locale.US)
                .format(new Date(System.currentTimeMillis() - 24*60*60*1000));
    }
    
    /**
     * saves the current Time in SharedPreferences
     */
    private void saveTime() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getContext().getString(R.string.sharedPref_fileName), Context.MODE_PRIVATE);
    
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getContext().getString(R.string.syncService_sharedPref_timeKey),
                new SimpleDateFormat(getContext().getString(R.string.rfc3339_format), Locale.US).format(new Date()));
        editor.apply();
    }
    
    
    
    /*
        static Helper methods
     */
    
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        ContentResolver.requestSync(new SyncRequest.Builder()
                .syncPeriodic(syncInterval, flexTime)
                .setSyncAdapter(getSyncAccount(context),
                        context.getString(de.pascaldierich.model.R.string.content_authority))
                .setExtras(new Bundle())
                .build());
    }
    
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(de.pascaldierich.model.R.string.content_authority),
                bundle);
    }
    
    public static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        
        Account newAccount = new Account(
                context.getString(R.string.app_name),
                context.getString(R.string.sync_account_type));
        
        if (accountManager.getPassword(newAccount) == null) {
            Log.d("SyncAdapter", "getSyncAccount: password is null");
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }
    
    private static void onAccountCreated(Account newAccount, Context context) {
        WatchdogSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, FLEX_TIME);
        
        ContentResolver.setSyncAutomatically(newAccount,
                context.getString(de.pascaldierich.model.R.string.content_authority), true);
        
        syncImmediately(context);
    }
    
    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
    
}
