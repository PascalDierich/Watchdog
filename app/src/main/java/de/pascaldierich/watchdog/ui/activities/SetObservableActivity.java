package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.activities.dialog.Presenter;
import de.pascaldierich.watchdog.presenter.activities.dialog.SetObservablePresenter;
import hugo.weaving.DebugLog;

/**
 * Activity to add or change Observables
 * TODO: will get moved to a Fragment (-> twoPaneMode)
 */
public class SetObservableActivity extends AppCompatActivity implements SetObservablePresenter.View {
    private static final String LOG_TAG = SetObservableActivity.class.getSimpleName();
    
    /*
        TODO: 03.03.17 twoPaneMode: change to Fragment with empty Activity
    */
    
    private Presenter mPresenter;
    
    /* Layout */
    @Nullable
    @BindView(R.id.setObservable_progressBar)
    ProgressBar mProgressBar;
    @Nullable
    @BindView(R.id.setObservable_textName)
    EditText mTextName;
    @Nullable
    @BindView(R.id.setObservable_fab)
    FloatingActionButton mFab;
    // YouTube
    @Nullable
    @BindView(R.id.setObservable_textYouTubeName)
    EditText mTextYouTube;
    @Nullable
    @BindView(R.id.switch_YouTube)
    Switch mSwitchYouTube;
    @Nullable
    @BindView(R.id.checkBox_YouTube)
    CheckBox mCheckBoxYouTube;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_observable);
        
        ButterKnife.bind(this);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    /**
     * changes the visibility of the progressBar
     */
    @Override
    public void changeProgressVisibility() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * show given Observable and related Sites if exists
     * <p/>
     * @param observable, Observable: existing Observable from model
     * @param sites, Site[]: related Sites
     */
    @Override
    public void setData(@Nullable Observable observable, @Nullable Site[] sites) {
        if (observable == null) return;
        
        mTextName.setText(observable.getDisplayName());
        if (observable.getGotThumbnail()) {
            // TODO: 02.03.17 show Thumbnail
        }
        
        if (sites == null || sites.length == 0) return;

        /* in later versions there are going to be more than one network */
        for (int i = 0; i < sites.length; i++) {
            switch (sites[i].getSite()) {
                case SupportedNetworks.YOUTUBE: {
                    mTextYouTube.setText(sites[i].getKey());
                    // TODO: 02.03.17 and show specific logo
                    break;
                }
                
            }
        }
    }
    
    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public Context getContext() {
        return this;
    }
    
    @Override
    public void showError() {
        Toast.makeText(this, "UNKNOWN ERROR", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * onClickListener for FAB
     */
    @OnClick(R.id.setObservable_fab)
    void fabClicked() {
        try {
            mPresenter.onSaveClicked(
                    mTextName.getText().toString());
        } catch (NullPointerException npe) {
            mPresenter.onError(1); // TODO: 03.03.17 define Error-Codes (see getSites below)
        }
    }
    
    /**
     * gets called by state-change for switch YouTube
     * calls Presenter to check for id
     * <p/>
     *
     * @param checked, boolean
     */
    @DebugLog
    @OnCheckedChanged(R.id.switch_YouTube)
    void onSwitchChangedYouTube(boolean checked) {
        mPresenter.checkIdYouTube(checked);
    }
    
    /**
     * sets the YouTube-CheckBox to indicate for the user if an Observable got found
     * <p/>
     * @param checked, boolean: true -> Observable found
     */
    @Override
    public void setCheckBoxYouTube(boolean checked) {
        mCheckBoxYouTube.setChecked(checked);
    }
    
    /**
     * returns the Text in the YouTubeEditText-View
     * <p/>
     * @return user-input, String: Channel name
     * @throws NullPointerException, if not usable input
     */
    @Override
    public String getTextYouTube() throws NullPointerException {
        return mTextYouTube.getText().toString();
    }
}

