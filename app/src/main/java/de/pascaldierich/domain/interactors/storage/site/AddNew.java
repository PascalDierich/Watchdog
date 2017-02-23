package de.pascaldierich.domain.interactors.storage.site;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.base.AbstractInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Site;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Interactor to save a new Site to intern storage
 */
public class AddNew extends AbstractInteractor implements AddNewInteractor {
    private Callback mCallback;
    private WeakReference<Context> wContext;
    private Site mItem;

    /**
     * Constructor
     * <p>
     *
     * @param executor, Executor:
     * @param mainThread, MainThread:
     * @param callback, AddNewInteractor.Callback: usually represented by 'this'
     * @param context, WeakReference<Context>: Context to access DB
     * @param item, Site: new Site to store
     */
    public AddNew(@NonNull Executor executor, @NonNull MainThread mainThread,
                  @NonNull Callback callback, @NonNull WeakReference<Context> context,
                  @Nullable Site item) {
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
     * set a new Site to store
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

            ApiConnector.getApi().get().setSite(wContext.get(), mItem);

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
