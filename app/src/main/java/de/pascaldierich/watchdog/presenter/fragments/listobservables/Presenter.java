package de.pascaldierich.watchdog.presenter.fragments.listobservables;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import de.pascaldierich.watchdog.ui.adapter.ObservablesContainerAdapter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractObservableListPresenter
        implements ObservableListPresenter, de.pascaldierich.domain.interactors.storage.StorageInteractor.GetCallback,
        ObservablesContainerAdapter.AdapterCallback {
    
    private ObservableListPresenter.View mView;

    /*
        Instantiation
     */
    
    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      ObservableListPresenter.View view) {
        super(executor, mainThread, savedInstance);
        
        mView = view;
    }
    
    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                     ObservableListPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }
    
    
    
    /*
        Initial Methods
     */
    
    @DebugLog
    @Override
    public void onStart() {
        // read out intern Storage to get all Observables
        super.getObservables(mView.getContext(), this);
    }
    
    @Override
    public void onResume() {
        
    }
    
    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        mView.showError();
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
    
    
    
    /*
        View Methods
            --> AdapterCallback
     */
    
    /**
     * Callback from Adapter -> ObservableList
     * <p/>
     * @param index, int: position in Observable-Collection which got selected
     */
    @Override
    public void onCardViewClick(int index) {
        if (index < 0) return;
        if (mObservables.get(index) == null) return;
    
        // TODO: 06.03.17 send either to MainActivity || PostsActivity
        mView.sendObservableToMain( // TODO: 06.03.17 change Method-Name to sendToCallback
                mObservables.get(index));
    }
    
    
    
    /*
        StorageInteractor Callbacks
     */
    
    @DebugLog
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        // TODO: 27.02.17 define Error-Codes
        onError(-1);
    }
    
    /**
     * onSuccess method of Get-Callbacks.
     * unchecked Cast to Observable
     *
     * @param result, ArrayList<?>: Collection of queried data as POJO of 'domainmodels'
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(@NonNull ArrayList<?> result) {
        try {
            super.mObservables = (ArrayList<Observable>) result;
        } catch (ClassCastException e) {
            onFailure(ModelErrorsCodes.UNKNOWN_FATAL_ERROR);
        }
        mView.setData(super.mObservables);
        mView.sendObservableToMain(super.mObservables.get(0));
    }
}
