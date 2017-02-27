package de.pascaldierich.watchdog.presenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;

public abstract class AbstractPresenter {

    protected Executor mExecutor;
    protected MainThread mMainThread;
    protected Bundle mSavedInstance;

    protected AbstractPresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        mExecutor = executor;
        mMainThread = mainThread;
        mSavedInstance = savedInstance;
    }
}
