package de.pascaldierich.watchdog.ui.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.activities.setobservable.Presenter;
import de.pascaldierich.watchdog.presenter.activities.setobservable.SetObservablePresenter;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

/**
 * Activity-Container to hold SetObservableFragment in onePaneMode
 */
public class SetObservableActivity extends AppCompatActivity implements SetObservablePresenter.View {
    
    /*
        Instantiation
     */
    
    private Presenter mPresenter;
    private Fragment mFragment;
    
    // Fragment Tag
    private static final String SET_OBSERVABLE_FRAGMENT_TAG = "SO_FragmentTag";
    
    // Layout
    @BindView(R.id.save_Observable)
    FloatingActionButton mFab;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_observable);
        setupWindowAnimations();
    
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, SET_OBSERVABLE_FRAGMENT_TAG);
        } else {
            mFragment = new SetObservableFragment();
        }
        
        ButterKnife.bind(this);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
    }
    
    
    /*
        Initial Methods
     */
    
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    @Override
    public Context getContext() {
        return this;
    }
    
    @Override
    public void showError() {
        
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    
        getSupportFragmentManager().putFragment(savedInstanceState, SET_OBSERVABLE_FRAGMENT_TAG, mFragment);
    }
    
    
    
    
    
    /*
        View Methods for Presenter
     */
    
    /**
     * returns this.getIntent to get Observable-Parameter
     * <p/>
     *
     * @return intent, getIntent();
     */
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
    public void setFragment(SetObservableFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setObservable_container, mFragment, SET_OBSERVABLE_FRAGMENT_TAG)
                .commit();
    }
    
    /**
     *
     */
    @OnClick(R.id.save_Observable)
    void onClick() {
        mPresenter.onSaveClicked();
    }
    
    /*
        Methods to start/update fragments/activities
     */
    
    /**
     * start the MainActivity.
     * gets called by Presenter.onSaveClicked().
     */
    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
    
    
    /**
     * This Interface gets implemented in SetObservableFragment.
     */
    public interface SetObservableCallback {
    
        /**
         * Checks if user-input is correct
         * <p/>
         * @return boolean, true -> input workable
         */
        boolean inputVerified();
    
        /**
         * returns the Observable set by User
         * <p/>
         * @return Observable, null
         */
        @Nullable
        Observable getObservableCallback();
    
        /**
         * return the Site-Collection
         * <p/>
         * @return Site-Collection
         */
        ArrayList<Site> getSitesCallback();
        
    }
    
    
    
    /*
        private Methods
     */
    
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }
}

