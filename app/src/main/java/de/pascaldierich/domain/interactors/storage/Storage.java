package de.pascaldierich.domain.interactors.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.base.AbstractInteractor;

/**
 * Superclass of Storage-Interactors.
 */
public abstract class Storage extends AbstractInteractor implements StorageInteractor {

    // Context for accessing DB
    public Context mContext;

    /**
     * Constructor for Storage-Interactors
     * <p>
     *
     * @param threadExecutor
     * @param mainThread
     * @param context context, WeakReference<Context>: Context to access DB
     */
    public Storage(@NonNull Executor threadExecutor, @NonNull MainThread mainThread,
                   @NonNull Context context) {
        super(threadExecutor, mainThread);
        mContext = context;
    }

    /**
     * Set a new Context
     * <p>
     *
     * @param context, WeakReference<Context>: Context to access DB
     */
    @Override
    public void setContext(@NonNull Context context) {
        mContext = context;
    }
}
