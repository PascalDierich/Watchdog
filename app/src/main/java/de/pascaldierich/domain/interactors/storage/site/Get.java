package de.pascaldierich.domain.interactors.storage.site;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Site;

/**
 * Interactor to get a Site from intern storage
 */
@Deprecated
public class Get extends Storage implements StorageInteractor {
    private StorageInteractor.GetCallback mCallback;
    private int mObservableId = -1;

    /**
     * Use this constructor if you don't know the observableId yet
     * or want to query for all Site's in storage
     * <p>
     *
     * @param threadExecutor
     * @param mainThread
     * @param context,       WeakReference<Context>: Context to access DB
     * @param callback,      StorageInteractor.GetCallback: usually represented by 'this'
     */
    public Get(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
               @NonNull Context context,
               @NonNull StorageInteractor.GetCallback callback) {
        super(threadExecutor, mainThread, context);

        mCallback = callback;
    }

    /**
     * Use this constructor if you already know the <b>observableId</b>
     * and want to query for this specific Observable
     * <p>
     *
     * @param threadExecutor
     * @param mainThread
     * @param context,       WeakReference<Context>: Context to access DB
     * @param callback,      StorageInteractor.GetCallback: usually represented by 'this'
     * @param observableId,  int: unique Observable Id defined by model
     */
    public Get(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
               @NonNull Context context,
               @NonNull StorageInteractor.GetCallback callback, int observableId) {
        super(threadExecutor, mainThread, context);

        mCallback = callback;
        mObservableId = observableId;
    }

    /**
     * Set a new ObservableId.
     * <p>
     *
     * @param observableId, int: unique Observable Id defined by model
     */
    public void setObservableId(int observableId) {
        mObservableId = observableId;
    }

    /**
     * run Interactor
     */
    @Override
    public void run() {
        try {
            final ArrayList<Site> result;
            if (mObservableId < 1)
                result = ApiConnector.getApi().get().getSites(mContext);
            else
                result = ApiConnector.getApi().get().getSites(mContext, mObservableId);

            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess(result);
                }
            });
        } catch (final ModelException modelE) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    FirebaseCrash.log("setObservable: " + modelE.getErrorCode() + " \n" +
                            "postFailure in " + Get.class.getSimpleName());
                    
                    mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
