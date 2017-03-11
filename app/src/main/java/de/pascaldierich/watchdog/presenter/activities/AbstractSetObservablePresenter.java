package de.pascaldierich.watchdog.presenter.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.observable.Set;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;

public abstract class AbstractSetObservablePresenter extends AbstractPresenter {
    
    private long mObservableId;
    
    /**
     * @see {@link AbstractPresenter}
     */
    public AbstractSetObservablePresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
    }
    
    public void setObservable(Observable item, Context context,
                       StorageInteractor.SetCallback presenter) {
        WeakReference<Set> wInteractor = new WeakReference<Set>(new Set(
                super.mExecutor,
                super.mMainThread,
                context,
                presenter,
                item
        ));
        
        wInteractor.get().execute();
    }
    
    public void setSites(@NonNull ArrayList<Site> items, Context context,
                  StorageInteractor.SetCallback presenter) {
        WeakReference<de.pascaldierich.domain.interactors.storage.site.Set> wInteractor =
                new WeakReference<de.pascaldierich.domain.interactors.storage.site.Set>(
                        new de.pascaldierich.domain.interactors.storage.site.Set(
                                super.mExecutor,
                                super.mMainThread,
                                context,
                                presenter,
                                null));
        
//        for (int i = 0; i < items.size(); i++) {
//            wInteractor.get().setItem(items.get(i).setUserId((int) mObservableId));
//            wInteractor.get().execute();
//        }
        
        /* Right now there's only one supported Network, so we cheat a bit */
        /* This is 'necessary' because right now we get double-entries */
        wInteractor.get().setItem(items.get(items.size()-1).setUserId((int) mObservableId)); // we use the latest checked entry
        wInteractor.get().execute();
        
    }
    
    public void setObservableId(long observableId) {
        mObservableId = observableId;
    }
}
