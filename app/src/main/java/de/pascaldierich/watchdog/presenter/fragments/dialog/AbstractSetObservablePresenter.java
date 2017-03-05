package de.pascaldierich.watchdog.presenter.fragments.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.network.GetIdInteractor;
import de.pascaldierich.domain.interactors.network.YouTube;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.observable.Set;
import de.pascaldierich.domain.interactors.storage.site.Get;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import hugo.weaving.DebugLog;

abstract class AbstractSetObservablePresenter extends AbstractPresenter {
    
    /**
     * max number of Result for Id-Request
     */
    private final int RANGE = 10;
    
    private long mObservableId;
    
    ArrayList<Site> mSiteArrayList;
    
    /**
     * @see {@link AbstractPresenter}
     */
    AbstractSetObservablePresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
    
        mSiteArrayList = new ArrayList<>();
    }
    
    @DebugLog
    void getPossibleIds(@SupportedNetworks String site, @NonNull String name,
                                  GetIdInteractor.GetIdCallback presenter) {
        switch (site) {
            case SupportedNetworks.YOUTUBE: {
                WeakReference<YouTube> wInteractor = new WeakReference<YouTube>(new YouTube(
                        super.mExecutor,
                        super.mMainThread,
                        presenter,
                        name,
                        RANGE
                ));
                
                wInteractor.get().execute();
            }
            default: {
                // TODO: 28.02.17 define Error-routine
            }
        }
    }
    
    @DebugLog
    void getSitesInteractor(int observableId, Context context,
                            StorageInteractor.GetCallback presenter) {
        WeakReference<Get> wInteractor = new WeakReference<Get>(new Get(
                super.mExecutor,
                super.mMainThread,
                context,
                presenter,
                observableId
        ));
        
        wInteractor.get().execute();
    }
    
    
    
    
    
    
    
    @DebugLog
    @Deprecated
    void setObservable(Observable item, Context context,
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
    
    @DebugLog
    @Deprecated
    void setSites(@NonNull ArrayList<Site> items, Context context,
                            StorageInteractor.SetCallback presenter) {
        WeakReference<de.pascaldierich.domain.interactors.storage.site.Set> wInteractor =
                new WeakReference<de.pascaldierich.domain.interactors.storage.site.Set>(
                        new de.pascaldierich.domain.interactors.storage.site.Set(
                                super.mExecutor,
                                super.mMainThread,
                                context,
                                presenter,
                                null));
        
        for (int i = 0; i < items.size(); i++) {
            wInteractor.get().setItem(items.get(i).setUserId((int) mObservableId));
            wInteractor.get().execute();
        }
        
    }
    
    @Deprecated
    void setObservableId(long observableId) {
        mObservableId = observableId;
    }
    
}
