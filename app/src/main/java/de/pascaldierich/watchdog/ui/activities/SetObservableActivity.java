package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

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
    
    // Fragment Tag
    private static final String SET_OBSERVABLE_FRAGMENT_TAG = "SO_FragmentTag";
    
    // Layout
    @BindView(R.id.save_Observable)
    FloatingActionButton mFab;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_observable);
        
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
                .replace(R.id.setObservable_container, fragment, SET_OBSERVABLE_FRAGMENT_TAG)
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
        startActivity(new Intent(this, MainActivity.class));
    }
    
    
    /**
     * This Interface gets implemented in SetObservableFragment.
     */
    public interface SetObservableCallback {
        
        boolean inputVerified();
        
        Observable getObservableCallback();
        
        ArrayList<Site> getSitesCallback();
        
    }
}

