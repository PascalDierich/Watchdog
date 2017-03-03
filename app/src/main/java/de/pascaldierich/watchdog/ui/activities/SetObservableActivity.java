package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
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

public class SetObservableActivity extends AppCompatActivity implements SetObservablePresenter.View {
    private static final String LOG_TAG = SetObservableActivity.class.getSimpleName();

    private Presenter mPresenter;

    // Layout
    @Nullable
    @BindView(R.id.setObservable_progressBar)
    ProgressBar mProgressBar;
    @Nullable
    @BindView(R.id.setObservable_textName)
    EditText mTextName;
    @Nullable
    @BindView(R.id.setObservable_textYouTubeName)
    EditText mTextYouTube;
    @Nullable
    @BindView(R.id.setObservable_fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_observable);

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
     *
     * @param observable, Observable: existing Observable from db
     * @param sites,      Site[]: related Sites
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
    public Context getContext() {
        return this;
    }

    @Override
    public void showError() {

    }

    @DebugLog
    @OnClick(R.id.setObservable_fab)
    void fabClicked() {
        // TODO: 03.03.17 doesn't click
        try {
            mPresenter.onSaveClicked(
                    mTextName.getText().toString(),
                    null, // TODO: 03.03.17 get Bitmap
                    getSites()
            );
        } catch (NullPointerException npe) {
            mPresenter.onError(1); // TODO: 03.03.17 define Error-Codes (see getSites below)
        }
    }
    
    private ArrayList<Site> getSites() throws NullPointerException {
        ArrayList<Site> result = new ArrayList<>();
    
        // read out EditText for each Network
        
        /* YouTube */
        if (mTextYouTube.getText() != null && !mTextYouTube.getText().toString().isEmpty()) {
            result.add(new Site()
                    .setSite(SupportedNetworks.YOUTUBE)
                    .setKey(mTextYouTube.getText().toString()));
        } else {
            throw new NullPointerException(SupportedNetworks.YOUTUBE);
        }
        
        // [...]
        
        return result;
    }

}

