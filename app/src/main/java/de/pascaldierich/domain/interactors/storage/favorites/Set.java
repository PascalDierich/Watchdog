package de.pascaldierich.domain.interactors.storage.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;

/**
 * Interactor to save a new Post in 'Favorites'
 */
public class Set extends Storage implements StorageInteractor {
    private StorageInteractor.SetCallback mCallback;
    private Post mItem;

    /**
     * Constructor
     * <p>
     *
     * @param executor,   Executor:
     * @param mainThread, MainThread:
     * @param callback,   AddNewInteractor.Callback: usually represented by 'this'
     * @param context,    WeakReference<Context>: Context to access DB
     * @param item,       Site: new Site to store
     */
    public Set(@NonNull Executor executor, @NonNull MainThread mainThread,
               @NonNull WeakReference<Context> context,
               @NonNull StorageInteractor.SetCallback callback,
               @Nullable Post item) {
        super(executor, mainThread, context);

        mCallback = callback;
        mItem = item;
    }

    /**
     * Set a new Post
     * <p>
     *
     * @param item, Post: new Post to store
     */
    public void setItem(@NonNull Post item) {
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
                        mCallback.onFailure(-1); // TODO define Interactor ErrorCodes
                    }
                });
            }

            ApiConnector.getApi().get().setFavorite(wContext.get(), mItem);

            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess();
                }
            });
        } catch (final ModelException modelE) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
