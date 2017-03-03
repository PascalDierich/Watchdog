package de.pascaldierich.watchdog.presenter.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.R;
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
        mView.startActivity(new Intent()
                .putExtra(mView.getContext().getString(R.string.observableKey_displayName), mObservables.get(index).getDisplayName())
                .putExtra(mView.getContext().getString(R.string.observableKey_observableId), mObservables.get(index).getUserId())
                .putExtra(mView.getContext().getString(R.string.observableKey_gotThumbnail), mObservables.get(index).getGotThumbnail())
                .putExtra(mView.getContext().getString(R.string.observableKey_thumbnail), mObservables.get(index).getThumbnail()));
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
