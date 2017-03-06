package de.pascaldierich.watchdog.presenter.activities.posts;

import android.os.Bundle;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import de.pascaldierich.watchdog.ui.fragments.PostsFragment;

public class Presenter implements PostsPresenter {
    
    private PostsPresenter.View mView;
    private PostsFragment mFragment;
    
    /*
        Instantiation
     */
    
    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      PostsPresenter.View view) {
        mView  = view;
        mFragment = new PostsFragment();
    }
    
    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                     PostsPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }
    
    
    
    /*
        Initial Method
     */
    
    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        Bundle args = new Bundle();
        args.putParcelable(mView.getContext().getString(R.string.parcelable_observable),
                mView.getIntentExtra().getParcelableExtra(mView.getContext().getString(R.string.parcelable_observable)));
        mFragment.setArguments(args);
        
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
    
    
}
