package de.pascaldierich.domain.interactors.storage.site;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.crash.FirebaseCrash;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.*;
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
                        FirebaseCrash.log("setSite: Item is null. \n" +
                                "postFailure in " + Set.class.getSimpleName());
                        
                        mCallback.onFailure(-1);
                    }
                });
            }

            final long id = ApiConnector.getApi().get().setSite(mContext, mItem);

            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess(id, false);
                }
            });
        } catch (final ModelException modelE) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    FirebaseCrash.log("setSite: " + modelE.getErrorCode() + " \n" +
                            "postFailure in " + Set.class.getSimpleName());
                    
                    mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
