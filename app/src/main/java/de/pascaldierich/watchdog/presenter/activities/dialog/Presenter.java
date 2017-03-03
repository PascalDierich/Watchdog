package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter {
    private static final String LOG_TAG = Presenter.class.getSimpleName();

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
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        mView.setData(null, null); // TODO: 02.03.17 get Data 
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
        Log.d(LOG_TAG, "onError: errorCode = " + errorCode);
    }
    
    @Override
    public void onSaveClicked(String displayName, @Nullable Bitmap thumbnail, ArrayList<Site> sites) {
        
    }
}
