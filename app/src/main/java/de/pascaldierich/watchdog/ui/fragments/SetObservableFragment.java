package de.pascaldierich.watchdog.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.dialog.Presenter;
import de.pascaldierich.watchdog.presenter.fragments.dialog.SetObservablePresenter;

public class SetObservableFragment extends Fragment implements SetObservablePresenter.View {
    private static final String LOG_TAG = SetObservableFragment.class.getSimpleName();
    
    private Presenter mPresenter;
    
    private View mRootView;
    
    // Layout
    @Nullable @BindView(R.id.setObservable_progressBar)
    ProgressBar mProgressBar;
    @Nullable @BindView(R.id.setObservable_textName)
    EditText mTextName;
    @Nullable @BindView(R.id.setObservable_textYouTubeName)
    EditText mTextYouTube;
    @Nullable @BindView(R.id.setObservable_fab)
    FloatingActionButton mFab;
    
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(false);

        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstance, this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_observable_list, container, false);
        ButterKnife.bind(this, mRootView);
        
        return mRootView;
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
     *
     * @param observable, Observable: existing Observable from db
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
        
    }
    
    @Override
    public void showError() {
        
    }
}
