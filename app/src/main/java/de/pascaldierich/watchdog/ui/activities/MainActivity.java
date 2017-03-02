package de.pascaldierich.watchdog.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.sync.WatchdogSyncAdapter;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.activities.main.MainPresenter;
import de.pascaldierich.watchdog.presenter.activities.main.Presenter;
import de.pascaldierich.watchdog.ui.fragments.ObservableListFragment;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private Presenter mPresenter;

    private static final String OBSERVABLE_LIST_FRAGMENT_TAG = "OL_FragmentTag";
    private static final String POST_LIST_FRAGMENT_TAG = "PL_FragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    .replace(R.id.postList_container, null, POST_LIST_FRAGMENT_TAG) // TODO: 28.02.17 create PostListFragment.class
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
     *      true -> twoPaneMode active ( > sw600dp )
     *      false -> twoPaneMode inactive ( < sw600dp )
     */
    public boolean getUiMode() {
        return findViewById(R.id.postList_container) != null;
    }

}