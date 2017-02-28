package de.pascaldierich.watchdog.presenter.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import hugo.weaving.DebugLog;

public abstract class AbstractMainPresenter extends AbstractPresenter {

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
    protected void getPossibleIds(@SupportedNetworks String site, @NonNull String name) {
        switch (site) {
            case SupportedNetworks.YOUTUBE: {
                // TODO: 28.02.17 add Interactor
            }
            default: {
                // TODO: 28.02.17 define Error-routine
            }
        }
    }
}
