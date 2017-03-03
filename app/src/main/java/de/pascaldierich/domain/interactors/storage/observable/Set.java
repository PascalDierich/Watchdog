package de.pascaldierich.domain.interactors.storage.observable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Observable;

/**
 * Interactor to save a new Observable to intern storage
 */
public class Set extends Storage implements StorageInteractor {
    private StorageInteractor.SetCallback mCallback;
    private Observable mItem;
    
    /**
     * Constructor for Storage-Interactors
     * <p>
     *
     * @param threadExecutor
     * @param mainThread
     * @param context        context, WeakReference<Context>: Context to access DB
     */
    public Set(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
               @NonNull Context context,
               @NonNull StorageInteractor.SetCallback callback,
               @Nullable Observable item) {
        super(threadExecutor, mainThread, context);
        
        mCallback = callback;
        mItem = item;
    }
    
    /**
     * Set a new Observable
     * <p>
     *
     * @param item, Observable: new Observable to store
     */
    public void setItem(@NonNull Observable item) {
        mItem = item;
    }
    
    /**
     * run Interactor
     */
    @Override
    public void run() {
        try {
            if (mItem == null) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.w("SetInteractor", "run: going to post onFailure");
                        mCallback.onFailure(-1); // TODO define Interactor ErrorCodes
                    }
                });
            }
            
            final long OBSERVABLE_ID = ApiConnector.getApi().get().setObservable(mContext, mItem);
            
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    Log.w("SetInteractor", "run: going to post onSuccess");
                    mCallback.onSuccess(OBSERVABLE_ID);
                }
            });
        } catch (final ModelException modelE) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    Log.w("SetInteractor", "run: going to post onFailure");
                    mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
