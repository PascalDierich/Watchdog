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

import de.pascaldierich.domain.interactors.service.Search;
import hugo.weaving.DebugLog;

public class WatchdogSyncAdapter extends AbstractThreadedSyncAdapter {


    public WatchdogSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @DebugLog
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        WeakReference<Search> wInteractor = new WeakReference<Search>(new Search(
                "",
                new WeakReference<Context>(getContext()), // TODO: define Parameter
                5
        ));

        wInteractor.get().run();
    }


    /*
        static Helper methods
     */

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        ContentResolver.requestSync(new SyncRequest.Builder()
                .syncPeriodic(syncInterval, flexTime)
                .setSyncAdapter(getSyncAccount(context), "") // TODO: add authority
                .setExtras(new Bundle())
                .build());
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                "", bundle); // TODO: add authority
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account newAccount = new Account(
                "", ""  // TODO: add app_name and sync_account_type
        );

        if (accountManager.getPassword(newAccount) == null) {

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        WatchdogSyncAdapter.configurePeriodicSync(context, 1, 1); // TODO: define Sync-intervals int, int

        ContentResolver.setSyncAutomatically(newAccount, "", true); // TODO: add authority

        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

}
