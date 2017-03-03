package de.pascaldierich.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.pascaldierich.domain.interactors.service.Search;
import de.pascaldierich.watchdog.R;
import hugo.weaving.DebugLog;

public class WatchdogSyncAdapter extends AbstractThreadedSyncAdapter {
    
    public static final int SYNC_INTERVAL = 60 * 120;
    public static final int FLEX_TIME = SYNC_INTERVAL / 2;
    
    public static final int RANGE = 5;
    
    public WatchdogSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }
    
    @DebugLog
    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(new Date());
    }
    
    @DebugLog
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        WeakReference<Search> wInteractor = new WeakReference<Search>(new Search(
                getTime(), // time
                getContext(),
                RANGE
        ));
        
        wInteractor.get().run();
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
