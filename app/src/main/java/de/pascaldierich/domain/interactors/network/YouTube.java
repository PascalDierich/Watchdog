package de.pascaldierich.domain.interactors.network;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.base.AbstractInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Site;

public class YouTube extends AbstractInteractor implements GetIdInteractor {
    private GetIdInteractor.GetIdCallback mCallback;
    private String mName;

    // TODO: 01.03.17 add int range parameter to constructor's
    public YouTube(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                   @NonNull GetIdInteractor.GetIdCallback callback) {
        super(threadExecutor, mainThread);

        mCallback = callback;
    }

    public YouTube(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                   @NonNull GetIdInteractor.GetIdCallback callback, String name) {
        super(threadExecutor, mainThread);

        mCallback = callback;
        mName = name;
    }

    /**
     * Set a new Name
     * <p>
     *
     * @param name
     */
    @Override
    public void setName(@NonNull String name) {
        mName = name;
    }

    /**
     * run Interactor
     */
    @Override
    public void run() {
        try {
            final ArrayList<Site> result
                    = ApiConnector.getApi().get().getIdYouTube(mName, 10); // TODO: 28.02.17 define global-range

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
                    mCallback.onFailure(modelEx.getErrorCode());
                }
            });
        }
    }
}
