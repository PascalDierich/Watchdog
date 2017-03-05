package de.pascaldierich.watchdog.presenter.activities.setobservable;

import android.os.Bundle;
import android.util.Log;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.watchdog.presenter.activities.AbstractSetObservablePresenter;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import de.pascaldierich.watchdog.ui.activities.SetObservableActivity;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter,
        StorageInteractor.SetCallback {
    
    private SetObservablePresenter.View mView;
    
    private SetObservableFragment mFragment;
    
    /*
        Instantiation
     */
    
    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      SetObservablePresenter.View view) {
        super(executor, mainThread, savedInstance);
        
        mView = view;
        mFragment = new SetObservableFragment();
    }
    
    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                              SetObservablePresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }
    
    
    
    /*
        Initial Methods
     */
    
    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        mView.setFragment(mFragment);
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
    
    
    
    /*
        View Methods
     */
    
    /**
     *
     */
    @Override
    public void onSaveClicked() {
        SetObservableActivity.ObservableCallback get;
        try {
            get = (SetObservableActivity.ObservableCallback) mFragment;
        } catch (ClassCastException e) {
            Log.e("SetObservablePresenter", mFragment.getClass().getSimpleName() + " must implement "
                    + " SetObservableActivity.Callback interface");
            mView.showError();
            return;
        }
        
    
        // TODO: 05.03.17 check data
        get.getObservable();
        get.getSites();
    
        super.setObservable(get.getObservable(), mView.getContext(), this);
        super.setSites(get.getSites(), mView.getContext(), this);
    }
    
    
    
    /*
        StorageInteractor.Set Callbacks
     */
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        mView.showError();
    }
    
    @Override
    public void onSuccess(long id) {
        mView.startMainActivity();
    }
}
