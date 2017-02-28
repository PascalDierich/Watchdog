package de.pascaldierich.watchdog.presenter.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.network.GetIdInteractor;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends  AbstractMainPresenter
        implements MainPresenter, StorageInteractor.SetCallback, GetIdInteractor.GetIdCallback {

    private MainPresenter.View mView;

    /*
        Instantiation
     */

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      MainPresenter.View view) {
        super(executor, mainThread, savedInstance);

        mView = view;
    }

    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                           MainPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }

    // StorageInteractor.SetCallback success method
    @Override
    public void onSuccess() {

    }

    // GetIdInteractor.GetIdCallback success method
    @Override
    public void onSuccess(@NonNull ArrayList<Site> result) {

    }

    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {

    }

    /**
     * onStart is used to get initialData.
     */
    @DebugLog
    @Override
    public void onStart() {
        mView.setUiMode(mView.getUiMode());
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {

    }


}
