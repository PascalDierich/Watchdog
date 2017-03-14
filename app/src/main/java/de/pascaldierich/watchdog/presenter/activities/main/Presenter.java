package de.pascaldierich.watchdog.presenter.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import de.pascaldierich.watchdog.ui.activities.SetObservableActivity;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public class Presenter extends AbstractMainPresenter
        implements MainPresenter, StorageInteractor.SetCallback {
    
    private MainPresenter.View mView;
    
    private SetObservableFragment mFragment;
    
    /**
     * boolean to indicate whether twoPaneMode is set or not.
     * -> device < 600dp || device >= 600dp
     */
    private boolean mTwoPaneMode;
    /**
     * boolean to indicate Fragment set in fragment-container
     * <b>NOTE</b> is only relevant when mTwoPaneMode == true
     * <p>
     * true  -> SetObservableFragment
     * false -> PostsFragment
     */
    private boolean mDetailView = false; // default is PostsFragment
    
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
    
    SetObservableActivity.SetObservableCallback callback;
    @Override
    public void onClickFab() {
        if (mDetailView) { // save Data set in SetObservableFragment after verifying it
            try {
                callback = (SetObservableActivity.SetObservableCallback) mView.getFragment();
            } catch (ClassCastException e) {
                Log.e("main.Presenter", mView.getFragment().getClass().getSimpleName() + " must implement "
                        + " SetObservableActivity.Callback interface");
                mView.showError();
                return;
            }
    
            /* if false, Fragment will show error */
            if (callback.inputVerified()) {
                super.setObservable(callback.getObservableCallback(), mView.getContext(), this);
//                super.setSites(callback.getSitesCallback(), mView.getContext(), this);
            }
            /* that's why we don't have to do anything... just wait for next onClick */
            
        } else { // start SetObservable
            if (mTwoPaneMode) { // start Fragment
                mDetailView = true;
                mFragment = new SetObservableFragment();
                mView.startSetObservableFragment(mFragment, null);
            } else { // start Activity
                mView.startSetObservableActivity(null);
            }
        }
    }
    
    /**
     * Method to start PostFragment || PostActivity
     * <p/>
     *
     * @param item, Observable: selected Observable to transmit
     * @param defaultArg, boolean: indicates if PostActivity should launch
     */
    /* ObservableListFragment */
    @Override
    public void onObservableSelected(@NonNull Observable item, boolean defaultArg) {
        // show Observable only if twoPaneMode = true
        // OR twoPaneMode = false && defaultArg = false -> so PostsActivity will not launched at app-launch
        if (mTwoPaneMode && mView.getGotInstanceState() && defaultArg) {
            return;
        }
        if (mTwoPaneMode && !mView.getGotInstanceState()) {
            mView.updatePostsFragment(item);
        } else if (!mTwoPaneMode && !defaultArg){
            mView.startPostsActivity(item);
        } else if (!mTwoPaneMode) {
            return;
        } else {
            mView.updatePostsFragment(item);
        }
    }
    
    
    
    
    /*
        Set StorageInteractor Callbacks
     */
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        mView.showError();
    }
    
    /**
     * @param id
     */
    @Override
    public void onSuccess(long id, boolean type) {
        mDetailView = false;
    
        if (id > 0 && type) { // setObservableCallback
            super.setObservableId(id);
            super.setSites(callback.getSitesCallback(), mView.getContext(), this);
        } else {
            mView.updatePostsFragment(null);
        }
    }
}
