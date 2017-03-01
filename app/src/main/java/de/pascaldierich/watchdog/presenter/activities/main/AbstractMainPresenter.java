package de.pascaldierich.watchdog.presenter.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.network.GetIdInteractor;
import de.pascaldierich.domain.interactors.network.YouTube;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import hugo.weaving.DebugLog;

abstract class AbstractMainPresenter extends AbstractPresenter {

    /**
     * max number of Result for Id-Request
     */
    private final int RANGE = 10;

    // Collection of new 'Sites'
    protected ArrayList<Site> mSites;

    /**
     * @see {@link AbstractPresenter}
     */
    protected AbstractMainPresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);

        mSites = new ArrayList<>();
    }

    @DebugLog
    protected void getPossibleIds(@SupportedNetworks String site, @NonNull String name,
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

                wInteractor.get().run();
            }
            default: {
                // TODO: 28.02.17 define Error-routine
            }
        }
    }
}
