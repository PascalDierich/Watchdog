package de.pascaldierich.watchdog.presenter.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractObservableListPresenter
        implements ObservableListPresenter, de.pascaldierich.domain.interactors.storage.StorageInteractor.GetCallback {
    
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
    
    @DebugLog
    @Override
    public void onStart() {
        // read out intern Storage to get all Observables
        super.getObservables(mView.getContext(), this);
    }
    
    @Override
    public void onResume() {
        
    }
    
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
    }
    
    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        mView.showError();
    }
    
    /**
     * called by onClickListener for CardView
     * <p/>
     *
     * @param index, int: indicates which Observable got selected
     */
    @Override
    public void onObservableSelected(int index) {
        if (index < 0) return;
        if (mObservables.get(index) == null) return;
        mView.sendObservableToMain(
                mObservables.get(index)
        );
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
}
