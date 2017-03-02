package de.pascaldierich.production;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.network.GetIdInteractor;
import de.pascaldierich.domain.interactors.network.YouTube;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Site;
import hugo.weaving.DebugLog;

public class ProSite implements GetIdInteractor.GetIdCallback {
    private static final String LOG_TAG = ProSite.class.getSimpleName();

    private Context mContext;

    @DebugLog
    public void addSite(Context context, @NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                        String name, @IntRange(from = 1, to = 50) int range)
            throws ModelException {

        mContext = context;
        Log.i(LOG_TAG, "addSite: going to request Site");

        createSite(threadExecutor, mainThread, name, 10);
    }

    public void removeSites(Context context) throws ModelException {
//        ApiConnector.getApi().get().removeObservable(context);
    }

    @DebugLog
    private void createSite(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                            String name, @IntRange(from = 1, to = 50) int range) {

        WeakReference<YouTube> interactor = new WeakReference<YouTube>(new YouTube(
                threadExecutor, mainThread, this, name, range
        ));

        interactor.get().execute();
    }

    /**
     * @param result
     */
    @Override
    public void onSuccess(@NonNull ArrayList<Site> result) {
        Log.d(LOG_TAG, "onSuccess: size = " + result.size());
        Log.d(LOG_TAG, "onSuccess: key = " + result.get(0).getKey());
        Log.d(LOG_TAG, "onSuccess: site = " + result.get(0).getSite());

//        for (Site mItem : result) {
//            try {
//                ApiConnector.getApi().get().setSite(mContext, mItem);
//            } catch (ModelException e) {
//                Log.d(LOG_TAG, "onSuccess: e.getErrorCode() = " + e.getErrorCode());
//            }
//        }
    }

    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {

    }
}
