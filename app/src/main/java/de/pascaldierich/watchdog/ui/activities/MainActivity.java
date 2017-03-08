package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

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
import de.pascaldierich.watchdog.ui.fragments.ObservableListFragment;
import de.pascaldierich.watchdog.ui.fragments.PostsFragment;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public class MainActivity extends AppCompatActivity implements MainPresenter.View,
        ObservableListFragment.ObservableSelectedCallback, PostsFragment.ImplicitIntentCallback {
    
    /*
        Instantiation
     */
    
    private Presenter mPresenter;
    
    // Fragment Tags for FragmentManager
    private static final String OBSERVABLE_LIST_FRAGMENT_TAG = "OL_FragmentTag";
    private static final String POST_LIST_FRAGMENT_TAG = "PL_FragmentTag";
    private static final String SET_OBSERVABLE_FRAGMENT_TAG = "SO_FragmentTag";
    
    // Layout
    @BindView(R.id.fab_newObservable)
    FloatingActionButton mFab;
    
    /*
        initial Methods
     */
    ObservableListFragment fragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ButterKnife.bind(this);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
        
        WatchdogSyncAdapter.initializeSyncAdapter(this);
//        WatchdogSyncAdapter.syncImmediately(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart(); // <--- commented out for test reason
    }
    
    @Override
    public void setUiMode(boolean twoPaneMode) {
        if (twoPaneMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PostsFragment(), POST_LIST_FRAGMENT_TAG)
                    .commit();
        }
        if (fragment == null) {
            fragment = new ObservableListFragment();
        }
        if (fragment.isAdded()) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.observableList_container, fragment, OBSERVABLE_LIST_FRAGMENT_TAG)
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
        return findViewById(R.id.fragment_container) != null;
    }
    
    
    
    /*
        View Methods for Presenter
     */
    
    /**
     *
     */
    @OnClick(R.id.fab_newObservable)
    void onClick() {
        mPresenter.onClickFab();
    }
    
    
    
    /*
        Methods to start/update fragments/activities
     */
    
    /**
     * starts the setObservable Activity
     * -> twoPaneMode = false
     * <p/>
     *      puts Extra in Intent (key = R.string.parcelable_observable)
     *      when observable != null
     *
     * @param observable
     */
    @Override
    public void startSetObservableActivity(@Nullable Observable observable) {
        if (observable == null) {
            startActivity(new Intent(this, SetObservableActivity.class));
        } else {
            startActivity(new Intent(this, SetObservableActivity.class)
                    .putExtra(getString(R.string.parcelable_observable), observable));
        }
    }
    
    /**
     * starts the setObservable Fragment
     * -> twoPaneMode = true
     * <p/>
     *      puts Bundle in Arguments (key = R.string.parcelable_observable)
     *      when observable != null
     *
     * @param observable
     */
    @Override
    public void startSetObservableFragment(@NonNull SetObservableFragment fragment, @Nullable Observable observable) {
        if (observable == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, SET_OBSERVABLE_FRAGMENT_TAG)
                    .commit();
        } else {
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.parcelable_observable), observable);
            
            fragment.setArguments(args);
            
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, SET_OBSERVABLE_FRAGMENT_TAG)
                    .commit();
        }
    }
    
    /**
     * starts the Posts Activity
     * -> twoPaneMode = false
     * <p/>
     *      puts Observable as Extra in Intent (key = R.string.parcelable_observable)
     *
     * @param observable
     */
    @Override
    public void startPostsActivity(@NonNull Observable observable) {
        startActivity(new Intent(this, PostsActivity.class)
                .putExtra(getString(R.string.parcelable_observable), observable));
    }
    
    /**
     * updates the Posts Fragment
     * -> twoPaneMode = true
     * <p/>
     *      puts Observable as Bundle in Arguments (key = R.string.parcelable_observable)
     *
     * @param observable
     */
    @Override
    public void updatePostsFragment(@NonNull Observable observable) {
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.parcelable_observable), observable);
    
        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(args);
    
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, POST_LIST_FRAGMENT_TAG)
                .commit();
    }
    
    /**
     * implicit intent to start
     * <p/>
     *
     * @param intent
     */
    @Override
    public void startImplicitIntent(@NonNull Intent intent) {
        startActivity(intent);
    }
    
    
    
    /*
        Fragment Callbacks
     */
    
    /* PostFragment Callback */
    @Override
    public void onStartIntent(@NonNull Intent intent) {
        startActivity(intent);
    }
    
    /* ObservableList Callback */
    @Override
    public void onObservableSelected(@NonNull Observable item, boolean defaultArg) {
        mPresenter.onObservableSelected(item, defaultArg);
    }
    
    
}