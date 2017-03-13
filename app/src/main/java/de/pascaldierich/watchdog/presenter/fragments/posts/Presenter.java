package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

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
        }
        if (mObservable == null) {
            return;
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
        Log.d("Presenter Post", "onError: " + errorCode);
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
    
            FirebaseCrash.log("ClassCastException: " + e.getMessage() + ". \n" +
                    "Storage GetCallback in " + Presenter.class.getSimpleName());
            
            mView.showError();
        }
    }
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        if (mView.getSelectedPage()) { // NewsFeed
            mView.setErrorMessage(mView.getContext().getString(R.string.error_noNewsFedd));
        } else { // Favorites
            mView.setErrorMessage(mView.getContext().getString(R.string.error_noFavorites));
        }
    }
    
    
    
    /*
        Set StorageInteractor Callback
     */
    
    @Override
    public void onSuccess(long id, boolean type) {
        Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.succeess_setStorage), Toast.LENGTH_SHORT).show();
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
        
        mView.sendIntentToActivity(createIntent(mPosts.get(index)));
    }
    
    
    
    /*
        private methods
     */
    
    private Intent createIntent(Post post) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(
                mView.getContext().getString(R.string.youtube_baseUrl) + post.getPostId()));
    }
}
