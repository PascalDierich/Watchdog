package de.pascaldierich.watchdog.presenter.fragments.listobservables;

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

abstract class AbstractObservableListPresenter extends AbstractPresenter {
    
    // Collection of all Observables in 'Observables'
    ArrayList<Observable> mObservables;
    
    /**
     * @see {@link AbstractPresenter}
     */
    AbstractObservableListPresenter(Executor executor, MainThread mainThread, Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
        mObservables = new ArrayList<>();
    }
    
    /**
     * @see {@link Get}
     */
    void getObservables(Context context, StorageInteractor.GetCallback presenter) {
//        WeakReference<Get> wInteractor = new WeakReference<Get>(new Get(
//                super.mExecutor,
//                super.mMainThread,
//                context,
//                presenter
//        ));
//
//        wInteractor.get().execute();
    
        WeakReference<de.pascaldierich.domain.interactors.storage.Get> testInteractor =
                new WeakReference<de.pascaldierich.domain.interactors.storage.Get>(
                        new de.pascaldierich.domain.interactors.storage.Get(context, presenter));
        
        testInteractor.get().getObservable();
        
    }
}
