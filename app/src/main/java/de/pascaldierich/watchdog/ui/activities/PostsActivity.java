package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.activities.posts.PostsPresenter;
import de.pascaldierich.watchdog.presenter.activities.posts.Presenter;
import de.pascaldierich.watchdog.ui.fragments.PostsFragment;

public class PostsActivity extends AppCompatActivity implements PostsPresenter.View,
    PostsFragment.ImplicitIntentCallback {
    
    /*
        Instantiation
     */
    
    Presenter mPresenter;
    
    // Fragment Tag for FragmentManager
    private static final String POST_LIST_FRAGMENT_TAG = "PL_FragmentTag";
    
    
    /*
        Initial Methods
     */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        
        ButterKnife.bind(this);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    @Override
    public Context getContext() {
        return this;
    }
    
    @Override
    public void showError() {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show(); // TODO: 06.03.17 Error-Routine
    }
    
    
    
    /*
        View Methods for Presenter
     */
    
    /**
     * returns this.getIntent to get Observable-Parameter
     * <p/>
     * key = key = R.string.parcelable_observable
     *
     * @return intent, getIntent();
     */
    @NonNull
    @Override
    public Intent getIntentExtra() {
        return this.getIntent();
    }
    
    /**
     * Replaces the container-placeholder with SetObservableFragment.class
     * <p/>
     *
     * @param fragment
     */
    @Override
    public void setFragment(PostsFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, POST_LIST_FRAGMENT_TAG)
                .commit();
    }
    
    
    
    /*
        PostsFragment Callback
     */
    
    @Override
    public void onStartIntent(@NonNull Intent intent) {
        startActivity(intent);
    }
}
