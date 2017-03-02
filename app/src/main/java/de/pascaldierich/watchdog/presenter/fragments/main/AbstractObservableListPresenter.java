package de.pascaldierich.watchdog.presenter.fragments.main;

import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.observable.Get;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import hugo.weaving.DebugLog;

public abstract class AbstractObservableListPresenter extends AbstractPresenter {

    // Collection of all Observables in 'Observables'
    protected ArrayList<Observable> mObservables;

    /**
     * @see {@link AbstractPresenter}
     */
    protected AbstractObservableListPresenter(Executor executor, MainThread mainThread, Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
        mObservables = new ArrayList<>();
    }

    /**
     * @see {@link Get}
     */
    @DebugLog
    protected void getObservables(Context context, StorageInteractor.GetCallback presenter) {

        WeakReference<Get> wInteractor = new WeakReference<Get>(new Get(
                super.mExecutor,
                super.mMainThread,
                context, // TODO: 02.03.17 remove WeakReference by context
                presenter
        ));

        wInteractor.get().run();
    }
}
