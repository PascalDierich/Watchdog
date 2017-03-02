package de.pascaldierich.domain.interactors.storage.site;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Site;

/**
 * Interactor to save a new Site to intern storage
 */
public class Set extends Storage implements StorageInteractor {
    private StorageInteractor.SetCallback mCallback;
    private Site mItem;

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
               @NonNull Context context,
               @NonNull StorageInteractor.SetCallback callback,
               @Nullable Site item) {
        super(executor, mainThread, context);

        mCallback = callback;
        mItem = item;
    }

    /**
     * Set a new Site
     * <p>
     *
     * @param item, Site: new Site to store
     */
    public void setItem(@NonNull Site item) {
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

            ApiConnector.getApi().get().setSite(mContext, mItem);

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
