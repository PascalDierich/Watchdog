package de.pascaldierich.watchdog.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.setobservable.Presenter;
import de.pascaldierich.watchdog.presenter.fragments.setobservable.SetObservablePresenter;
import de.pascaldierich.watchdog.ui.activities.SetObservableActivity;
import hugo.weaving.DebugLog;


public class SetObservableFragment extends Fragment implements SetObservablePresenter.View,
        SetObservableActivity.SetObservableCallback {
    
    /*
        Instantiation
     */
    
    private Presenter mPresenter;
    
    private View mRootView;
    
    /* Layout */
    @Nullable
    @BindView(R.id.setObservable_progressBar)
    ProgressBar mProgressBar;
    @Nullable
    @BindView(R.id.setObservable_textName)
    EditText mTextName;
    // YouTube
    @Nullable
    @BindView(R.id.setObservable_textYouTubeName)
    EditText mTextYouTube;
    @Nullable
    @BindView(R.id.switch_YouTube)
    Switch mSwitchYouTube;
    
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(false);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstance, this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_set_observable, container, false);
        ButterKnife.bind(this, mRootView);
        
        return mRootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    
        if (savedInstanceState != null) {
            mTextName.setText(getContext().getString(R.string.instanceState_displayName));
            mTextName.setText(getContext().getString(R.string.instanceState_youtubeName));
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString(getContext().getString(R.string.instanceState_displayName),
                mTextName.getText().toString());
        outState.putString(getContext().getString(R.string.instanceState_youtubeName),
                mTextYouTube.getText().toString());
    }
    
    /*
        Initial Methods
     */
    
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    /**
     * returns the args the fragment got created with.
     * Args contains (if != null) an Observable which should get edited.
     * <b>Note:</b> key := R.string.parcelable_observable
     * <p/>
     *
     * @return args, Bundle: if Bundle != null it contains an Observable-Object
     */
    @Nullable
    @Override
    public Bundle getArgumentsBundle() {
        return this.getArguments();
    }
    
    @Override
    public Context getContext() {
        return getActivity();
    }
    
    @Override
    public void showError() {
        Toast.makeText(getContext(), getContext().getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
    }
    
    
    
    /*
        View Methods for Presenter
     */
    
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
     * show given Observable if exists
     * <p/>
     *
     * @param observable, Observable: existing Observable from model
     */
    @Override
    public void setObservable(@NonNull Observable observable) {
        
        mTextName.setText(observable.getDisplayName());
        if (observable.getGotThumbnail()) {
            
        }
    }
    
    /**
     * show loaded Sites if Observable set
     * <p/>
     *
     * @param sites, ArrayList<Sites>: related Sites for set Observable
     */
    @Override
    public void setSites(@NonNull ArrayList<Site> sites) {
        for (int i = 0; i < sites.size(); i++) {
            switch (sites.get(i).getSite()) {
                case SupportedNetworks.YOUTUBE: {
                    mTextYouTube.setText(getContext().getString(R.string.setObservableActivity_editText_site_defined));
                }
                // [...] <-- ...
            }
        }
    }
    
    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * returns the Text set in the EditText for specific Network
     * <p/>
     *
     * @param network, String: Name of the Network for which the EditText works
     * @return user-input, String: Channel name
     * @throws NullPointerException, if not usable input
     */
    @Override
    public String getTextNetwork(String network) throws NullPointerException {
        switch (network) {
            case SupportedNetworks.YOUTUBE: {
                return mTextYouTube.getText().toString();
            }
            // [..]
        }
        throw new NullPointerException();
    }
    
    /**
     * change font-color of TextField for specific Site
     * <p/>
     *
     * @param site,  String: Site for which TextField the color get changed
     * @param color, int: colorCode
     */
    @Override
    public void setTextColor(@SupportedNetworks String site, int color) {
        switch (site) {
            case SupportedNetworks.YOUTUBE: {
                mTextYouTube.setTextColor(color);
                break;
            }
            // [..] <- when there will be more supported networks
        }
    }
    
    /**
     * return the Text from Name-Field
     * <p/>
     *
     * @return user-input, String: Name of Observable
     * @throws NullPointerException, if not usable input
     */
    @Override
    public String getTextDisplayName() throws NullPointerException {
        return mTextName.getText().toString();
    }
    
    @Override
    public void setTextDisplayName(String displayName) {
        mTextName.setText(displayName);
    }
    
    @Override
    public void setTextYouTubeName(String youTubeName) {
        mTextYouTube.setText(youTubeName);
    }
    
    /*
        View-Listener
     */
    
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
        mPresenter.onStateChanged(SupportedNetworks.YOUTUBE, checked);
    }
    
    @DebugLog
    @OnTextChanged(value = R.id.setObservable_textYouTubeName,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onAfterTextChangedYouTube(final Editable newText) {
        if (newText.length() == 20) {
            Toast.makeText(getContext(), getContext().getString(R.string.setObservableActivity_editText_charErrorMessage),
                    Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwitchYouTube.isChecked())
                    mPresenter.onInputChanged(SupportedNetworks.YOUTUBE, newText.toString());
            }
        }, 1000);
    }
    
    
    
    /*
        SetObservable Callback from SetObservableActivity.class
     */
    
    /**
     * Checks if user-input is correct
     * <p/>
     *
     * @return boolean, true -> input workable
     */
    @Override
    public boolean inputVerified() {
        if (mPresenter == null) {
            mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                    null, this);
        }
        return mPresenter.inputVerified();
    }
    
    /**
     * returns the Observable set by User
     * <p/>
     *
     * @return Observable, null
     */
    @Override
    public Observable getObservableCallback() {
        return mPresenter.getObservableCallback();
    }
    
    /**
     * return the Site-Collection
     * <p/>
     *
     * @return Site-Collection
     */
    @Override
    public ArrayList<Site> getSitesCallback() {
        return mPresenter.getSitesCallback();
    }
}
