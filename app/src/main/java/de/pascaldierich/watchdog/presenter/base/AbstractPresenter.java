package de.pascaldierich.watchdog.presenter.base;

import android.os.Bundle;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;

public class AbstractPresenter {

    protected Executor mExecutor;
    protected MainThread mMainThread;
    protected Bundle mSavedInstance;

    protected AbstractPresenter(Executor executor, MainThread mainThread, Bundle savedInstance) {
        mExecutor = executor;
        mMainThread = mainThread;
        mSavedInstance = savedInstance;
    }
}
