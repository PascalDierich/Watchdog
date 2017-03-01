package de.pascaldierich.domain.interactors.network;

import android.support.annotation.IntRange;
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
    private int mRange;

    public YouTube(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                   @NonNull GetIdInteractor.GetIdCallback callback, @IntRange(from = 1, to = 50) int range) {
        super(threadExecutor, mainThread);

        mCallback = callback;
        mRange = range;
    }

    public YouTube(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                   @NonNull GetIdInteractor.GetIdCallback callback, String name,
                   @IntRange(from = 1, to = 50) int range) {
        super(threadExecutor, mainThread);

        mCallback = callback;
        mName = name;
        mRange = range;
    }

    /**
     * Set a new Name
     * <p>
     *
     * @param name, String: Name to search for
     */
    @Override
    public void setName(@NonNull String name) {
        mName = name;
    }

    /**
     * Set a new Range
     * <p>
     *
     * @param range, int: max numbers of Result
     */
    @Override
    public void setRange(@IntRange(from = 1, to = 50) int range) {
        mRange = range;
    }

    /**
     * run Interactor
     */
    @Override
    public void run() {
        try {
            final ArrayList<Site> result
                    = ApiConnector.getApi().get().getIdYouTube(mName, mRange);

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
