package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import de.pascaldierich.watchdog.ui.adapter.PostsContainerAdapter;

public class Presenter extends AbstractPostPresenter
        implements PostPresenter, StorageInteractor.GetCallback, StorageInteractor.SetCallback,
        PostsContainerAdapter.AdapterCallback{
    
    private PostPresenter.View mView;
    
    private Observable mObservable;

    /*
        Instantiation
     */
    
    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      PostPresenter.View view) {
        super(executor, mainThread, savedInstance);
        
        mView = view;
    }
    
    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                     PostPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }
    
     
    
    /*
        Initial Methods
     */
    
    @Override
    public void onStart() {
        // if (Observable == null) -> something went wrong
        // We need Observable != null to 1. getPosts and 2. set ObservableLayout
        try {
            mObservable = mView.getArgumentsBundle().getParcelable(
                    mView.getContext().getString(R.string.parcelable_observable));
        } catch (NullPointerException npe) {
            return;
            // TODO: 07.03.17 set ObservableLayout to e.g. example data 
        }
        mView.showObservable(mObservable);
        super.getPosts(mView.getContext(), this, mView.getSelectedPage(), mObservable.getUserId());
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
        Get StorageInteractor Callback
     */
    
    /**
     * onSuccess method of Get-Callbacks.
     * unchecked Cast to Post
     * <p/>
     * @param result, ArrayList<?>: Collection of queried data as POJO of 'domainmodels'
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(@NonNull ArrayList<?> result) {
        try {
            super.mPosts = (ArrayList<Post>) result;
            mView.setData(super.mPosts);
        } catch (ClassCastException e) {
            // TODO: 10.03.17 report ot Firebase
            Log.d("PostPresenter", "ClassCastException in onSuccess: " + e.getMessage());
            mView.showError();
        }
    }
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        onError(errorCode);
    }
    
    
    
    /*
        Set StorageInteractor Callback
     */
    
    @Override
    public void onSuccess(long id) {
        // TODO: 28.02.17 notify user, e.g. show Toast
    }
    
    
    
    /*
        View Methods
     */
    
    @Override
    public void onPageChanged(boolean selectedPage) {
        if (mObservable == null) {
            return;
        }
        super.getPosts(mView.getContext(), this, selectedPage, mObservable.getUserId());
    }
    
    /**
     * saves given Post local on device
     * <p/>
     * @param post, Post: Post to save on device
     */
    @Override
    public void onSavePost(Post post) {
        super.setFavorites(mView.getContext(), this, post);
    }
    
    
    
    /*
        --> AdapterCallback
     */
    
    @Override
    public void onStartNetworkClicked(int index) {
        if (index < 0) return;
        if (mPosts.get(index) == null) return;
        
        mView.sendIntentToActivity(null);

//        mView.startImplicitIntent(null); // TODO: 06.03.17 call create Intent(Site site)
    }
    
    
    
    /*
        private methods
     */
    
    private Intent createIntent(Post post) {
        // TODO: 06.03.17 create intent with ACTION etc. 
        return null;
    }
}
