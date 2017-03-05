package de.pascaldierich.watchdog.presenter.activities.main;

import android.os.Bundle;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;

public class Presenter extends AbstractPresenter
        implements MainPresenter {
    
    private MainPresenter.View mView;
    
    private boolean mTwoPaneMode;

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
    
    
    
    /*
        Initial Methods
     */
    
    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        mTwoPaneMode = mView.getUiMode();
        mView.setUiMode(mTwoPaneMode);
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
    
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        
    }
    
    
    
    /*
        View Methods
     */
    
    @Override
    public void onClickFab() {
        if (mTwoPaneMode) { // start Fragment
            mView.startSetObservableFragment(null);
        } else { // start Activity
            mView.startSetObservableActivity(null);
        }
    }
    
    
    
    /*
        Fragment Callbacks
     */
    
    /**
     * Presenter implements 3 Callback-classes:
     *
     *      - ObservableListFragment.Callback
     *          -> onObservableSelected(Observable observable)
     *              --> onePaneMode = start PostActivity
     *              --> twoPaneMode = update PostFragment
     *          -> onObservableSetting(Observable observable)
     *              --> onePaneMode = start SetObservableActivity
     *              --> twoPaneMode = replace SetObservableFragment with PostFragment
     *
     *      - PostFragment.Callback
     *          -> onPostSelected(Post post)
     *              --> onePaneMode = start explicit Intent
     *              --> twoPaneMode =  ``    ``      ``
     *
     *      - SetObservableFragment.Callback
     *          -> TODO: Problem: Save button is in Activity. -> maybe in save in static holder?
     *
     */
    
}
