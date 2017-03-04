package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.sync.WatchdogSyncAdapter;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.activities.main.MainPresenter;
import de.pascaldierich.watchdog.presenter.activities.main.Presenter;
import de.pascaldierich.watchdog.ui.callbacks.MainActivityCallback;
import de.pascaldierich.watchdog.ui.fragments.ObservableListFragment;
import de.pascaldierich.watchdog.ui.fragments.PostsFragment;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public class MainActivity extends AppCompatActivity implements MainPresenter.View,
        MainActivityCallback {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    private Presenter mPresenter;
    
    // Fragment Tags for FragmentManager
    private static final String OBSERVABLE_LIST_FRAGMENT_TAG = "OL_FragmentTag";
    private static final String POST_LIST_FRAGMENT_TAG = "PL_FragmentTag";
    private static final String SET_OBSERVABLE_FRAGMENT_TAG = "SO_FragmentTag";
    
    // Layout
    @BindView(R.id.fab_newObservable)
    FloatingActionButton mFab;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ButterKnife.bind(this);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
        
        WatchdogSyncAdapter.initializeSyncAdapter(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    @Override
    public void setUiMode(boolean twoPaneMode) {
        if (twoPaneMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.postList_container, new PostsFragment(), POST_LIST_FRAGMENT_TAG)
                    .commit();
        }
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.observableList_container, new ObservableListFragment(), OBSERVABLE_LIST_FRAGMENT_TAG)
                .commit();
    }
    
    @Override
    public Context getContext() {
        return this;
    }
    
    @Override
    public void showError() {
        // very useful...
    }
    
    /**
     * @return twoPaneMode, boolean:
     * true -> twoPaneMode active ( > sw600dp )
     * false -> twoPaneMode inactive ( < sw600dp )
     */
    public boolean getUiMode() {
        return findViewById(R.id.postList_container) != null;
    }
    
    /**
     * this onClick method opens a dialog to create a new Observable.
     */
    @OnClick(R.id.fab_newObservable)
    void onClick() {
        mPresenter.onClickFab();
    }
    
    @Override
    public void startSetObservableActivity(boolean twoPaneMode) {
        if (twoPaneMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.postList_container, new SetObservableFragment(), SET_OBSERVABLE_FRAGMENT_TAG)
                    .commit();
        } else {
            // TODO: 04.03.17 create new empty Activity with SetObservableFragment
            startActivity(new Intent(this, SetObservableActivity.class));
        }
    }
    
    @Override
    public void onObservableSelected(@NonNull Observable item) {
        Log.d(LOG_TAG, "onObservableSelected: name = " + item.getDisplayName());
        Log.d(LOG_TAG, "onObservableSelected: name = " + item.getUserId());
    
    
        /*
        start Activity or Fragment (twoPaneMode)
         */
    
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.postList_container, new PostsFragment().setObservable(item), POST_LIST_FRAGMENT_TAG)
                .commit();
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    }
}