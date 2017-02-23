package de.pascaldierich.domain.interactors.storage.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.base.AbstractInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Interactor to save a new Post to 'Favorites' intern storage
 */
public class AddNew extends AbstractInteractor implements AddNewInteractor {
    private de.pascaldierich.domain.interactors.storage.newsfeed.AddNewInteractor.Callback mCallback;
    private WeakReference<Context> wContext;
    private Post mItem;

    /**
     * Constructor
     * <p>
     *
     * @param executor, Executor:
     * @param mainThread, MainThread:
     * @param callback, AddNewInteractor.Callback: usually represented by 'this'
     * @param context, WeakReference<Context>: Context to access DB
     * @param item, Post: new Post to store
     */
    public AddNew(@NonNull Executor executor, @NonNull MainThread mainThread,
                  @NonNull de.pascaldierich.domain.interactors.storage.newsfeed.AddNewInteractor.Callback callback, @NonNull WeakReference<Context> context,
                  @Nullable Post item) {
        super(executor, mainThread);

        mCallback = callback;
        wContext = context;
        mItem = item;
    }

    /**
     * Set a new Context
     * <p>
     *
     * @param context, WeakReference<Context>: Context to access DB
     */
    public void setContext(@NonNull WeakReference<Context> context) {
        this.wContext = context;
    }

    /**
     * set a new Post to store
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
                public void run() {mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
