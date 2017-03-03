package de.pascaldierich.watchdog.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.production.ProObservable;
import de.pascaldierich.production.ProSite;
import de.pascaldierich.sync.WatchdogSyncAdapter;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.main.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.fragments.main.Presenter;
import de.pascaldierich.watchdog.ui.adapter.ObservablesContainerAdapter;

/**
 * Fragment for MainActivity.
 * Presents the List of Observables
 */
public class ObservableListFragment extends Fragment implements ObservableListPresenter.View {
    private static final String LOG_TAG = ObservableListFragment.class.getSimpleName();
    
    private Presenter mPresenter;

    /* Layout */
    @BindView(R.id.observables_container)
    RecyclerView mObservablesContainer;
    
    private ObservablesContainerAdapter mAdapter;

    private View mRootView;

    @Override
    public void showError() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);

        mAdapter = new ObservablesContainerAdapter(null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_observable_list, container, false);
        ButterKnife.bind(this, mRootView);

        mObservablesContainer.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mObservablesContainer.setAdapter(mAdapter);

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * show Observables
     * <p/>
     * @param observables
     */
    @Override
    public void setData(ArrayList<Observable> observables) {
        mAdapter.setItems(observables);
        mAdapter.notifyDataSetChanged();
    }


    /********************************************************************************
        only for production:
            - menu for basic operations
     ********************************************************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.production_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_newObservables: {
                try {
                    ProObservable.addObservables(this.getContext());
                    break;
                } catch (ModelException e) {
                    Log.d(LOG_TAG, "onOptionsItemSelected: " + e.getErrorCode());
                }
            }
            case R.id.menu_newSites: {
                try {
                    ProSite site = new ProSite();
                    site.addSite(this.getContext(), ThreadExecutor.getInstance(),
                            MainThreadImpl.getInstance(), "SemperVideo", 10);
                    break;
                } catch (ModelException e) {
                    Log.d(LOG_TAG, "onOptionsItemSelected: " + e.getErrorCode());
                }
            }
            case R.id.menu_newFavorites: {
                
            }
            case R.id.menu_newNewsFeed: {
                
            }
            case R.id.menu_removeObservables: {
                try {
                    ProObservable.removeObservables(this.getContext());
                    break;
                } catch (ModelException e) {
                    Log.d(LOG_TAG, "onOptionsItemSelected: " + e.getErrorCode());
                }
            }
            case R.id.menu_removeSites: {

            }
            case R.id.menu_removeFavorites: {

            }
            case R.id.menu_removeNewsFeed: {

            }
            case R.id.startSync: {
                
//                ContentResolver.requestSync(WatchdogSyncAdapter.getSyncAccount(getContext()),
//                        "de.pascaldierich.watchdogs", new Bundle());
                WatchdogSyncAdapter.syncImmediately(getContext());
                break;
            }

            default:
                Toast.makeText(this.getContext(), "option number " + id, Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
