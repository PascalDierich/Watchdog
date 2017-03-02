package de.pascaldierich.watchdog.presenter.fragments.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;

abstract class AbstractSetObservablePresenter extends AbstractPresenter {

    /**
     * @see {@link AbstractPresenter}
     */
    protected AbstractSetObservablePresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);

    }

}
