package de.pascaldierich.domain.interactors.storage.observable;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Observable;
import hugo.weaving.DebugLog;

/**
 * Interactor to get an Observable from intern storage
 */
public class Get extends Storage implements StorageInteractor {
    private StorageInteractor.GetCallback mCallback;
    
    /**
     * Constructor
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
     * run Interactor
     */
    @DebugLog
    @Override
    public void run() {
        try {
            final ArrayList<Observable> result =
                    ApiConnector.getApi().get().getObservables(mContext);
            
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess(result);
                }
            });
        } catch (final ModelException modelEx) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onFailure(modelEx.getErrorCode()); // TODO: 23.02.17 define Interactor ErrorCodes
                }
            });
        }
    }
}
