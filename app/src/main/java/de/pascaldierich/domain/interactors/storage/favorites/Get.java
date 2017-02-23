package de.pascaldierich.domain.interactors.storage.favorites;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;

/**
 * Interactor to get a Post from 'Favorites'
 */
public class Get extends Storage implements StorageInteractor {
    private StorageInteractor.GetCallback mCallback;
    private int mObservableId = -1;

    /**
     * Use this constructor if you don't know the observableId yet
     * or want to query for all Post's in 'NewsFeed'
     * <p>
     *
     * @param threadExecutor
     * @param mainThread
     * @param context, WeakReference<Context>: Context to access DB
     * @param callback, StorageInteractor.GetCallback: usually represented by 'this'
     */
    public Get(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
               @NonNull WeakReference<Context> context,
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
     * @param context, WeakReference<Context>: Context to access DB
     * @param callback, StorageInteractor.GetCallback: usually represented by 'this'
     * @param observableId, int: unique Observable Id defined by model
     */
    public Get(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
               @NonNull WeakReference<Context> context,
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
            final ArrayList<Post> result;
            if (mObservableId < 1)
                result = ApiConnector.getApi().get().getFavorites(wContext.get());
            else
                result = ApiConnector.getApi().get().getFavorites(wContext.get(), mObservableId);

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