package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractPostPresenter
        implements PostPresenter, StorageInteractor.GetCallback, StorageInteractor.SetCallback {
    
    private PostPresenter.View mView;

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
    
    @DebugLog
    @Override
    public void onStart() {
        super.getPosts(mView.getContext(), this, mView.getSelectedPage());
    }
    
    @Override
    public void onResume() {
        
    }
    
    // Get Method Callback
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        onError(-1);
    }
    
    /**
     * onSuccess method of Get-Callbacks.
     * unchecked Cast to Post
     * @param result, ArrayList<?>: Collection of queried data as POJO of 'domainmodels'
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(@NonNull ArrayList<?> result) {
        try {
            super.mPosts = (ArrayList<Post>) result;
        } catch (ClassCastException e) {
            onFailure(ModelErrorsCodes.UNKNOWN_FATAL_ERROR);
        }
        mView.setData(super.mPosts);
    }
    
    // Set Method Callback
    @Override
    public void onSuccess(long id) {
        // TODO: 28.02.17 notify user, e.g. show Toast
    }
    
    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        mView.showError();
    }
    
    @Override
    public void onPageChanged(boolean selectedPage) {
        super.getPosts(mView.getContext(), this, selectedPage);
    }
    
    /**
     * saves given Post local on device
     * @param post, Post: Post to save on device
     */
    @Override
    public void onSavePost(Post post) {
        super.setFavorites(mView.getContext(), this, post);
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
