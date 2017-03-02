package de.pascaldierich.watchdog.presenter.fragments.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter {

    private SetObservablePresenter.View mView;

    /*
        Instantiation
     */

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      SetObservablePresenter.View view) {
        super(executor, mainThread, savedInstance);

        mView = view;
    }

    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                     SetObservablePresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }

    /**
     * called by FAB.onClickListener
     * finishes the Fragment and saves the new Observable
     */
    @Override
    public void onSavePressed() {
        mView.changeProgressVisibility();
        // TODO: 02.03.17 define
    }

    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        mView.setData(null, null);
    }

    @Nullable
    private Observable checkTransmittedData() {
        // TODO: 02.03.17 get Data
        return null;
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
