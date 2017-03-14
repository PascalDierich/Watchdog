package de.pascaldierich.watchdog.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.Converter;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.local.WatchdogContract;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.listobservables.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.fragments.listobservables.Presenter;
import de.pascaldierich.watchdog.ui.adapter.ObservablesContainerAdapter;

/**
 * Fragment for MainActivity.
 * Presents the List of Observables
 */
public class ObservableListFragment extends Fragment implements ObservableListPresenter.View,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = ObservableListFragment.class.getSimpleName();
    
    private static final int LOADER_ID = 1;
    
    /*
        Instantiation
     */
    
    private Presenter mPresenter;
    
    private ObservableListFragment.ObservableSelectedCallback mCallback;
    
    private ObservablesContainerAdapter mAdapter;
    
    private View mRootView;
    
    /* Layout */
    @BindView(R.id.observables_container)
    RecyclerView mObservablesContainer;
    
    
    
    /*
        Initial Methods
     */
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
        
        mAdapter = new ObservablesContainerAdapter(getContext(), null, mPresenter);
    
        /*
            TODO: 14.03.17 to get the Observables a Loader with LoaderManager and LoaderCallbacks is implemented
         */
        if (savedInstanceState == null) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
        
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ObservableListFragment.ObservableSelectedCallback) activity;
        } catch (ClassCastException cce) {
            Log.w(LOG_TAG, "onAttach: ClassCastException. " + activity.toString() + " must" +
                    "implement Callback");
        }
    }
    
    @Override
    public void showError() {
        
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }
    
    /**
     * show Observables
     * <p/>
     *
     * @param observables, ArrayList<Observables>: Observables-Collection received by StorageInteractor
     */
    @Override
    public void setData(ArrayList<Observable> observables) {
        mAdapter.setItems(observables);
    }
    
    
    
    /*
        Methods to start/update activities/fragments
     */
    
    /**
     * send the Observable to Callback (MainActivity XOR PostsActivity)
     * gets fired each time at CardView onClick event and
     * at launch by default.
     * <p/>
     *
     * @param observable, Observable: chosen Observable to transmit
     * @param defaultArg, boolean: true -> default start of method
     *                    false -> start of method because of active user-interaction
     */
    @Override
    public void sendObservableToCallback(@NonNull Observable observable, boolean defaultArg) {
        mCallback.onObservableSelected(observable, defaultArg);
    }
    
    
    /*
        Interface to send selected Observable to MainActivity
     */
    public interface ObservableSelectedCallback {
        
        /**
         * send selected Observable to MainActivity
         * <p/>
         *
         * @param observable, Observable: selected NonNull Observable
         * @param defaultArg, boolean: true -> starting routine to transmit fist Observable
         *                    false -> user actively choose this Observable
         */
        void onObservableSelected(@NonNull Observable observable, boolean defaultArg);
        
    }
    
    
    
    /*
        StorageCallback methods
     */
    
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader mLoader = new CursorLoader(getContext());
    
        mLoader.setUri(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES);
        mLoader.setProjection(new String[] {
                WatchdogContract.Observables.COLUMN_USER_ID,
                WatchdogContract.Observables.COLUMN_NAME,
                WatchdogContract.Observables.COLUMN_THUMBNAIL});
        mLoader.setSortOrder(WatchdogContract.Observables.COLUMN_USER_ID);
        
        return mLoader;
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try {
            final ArrayList<Observable> observables = new Converter().getObservable(data);
            
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mPresenter.setObservables(observables);
                    setData(observables);
                    sendObservableToCallback(observables.get(0), true);
                }
            });
        } catch (ModelException modelE) {
            FirebaseCrash.log("ModelException on ObservableListFragment: " + modelE.getErrorCode());
            
            showError();
        }
    }
    
    @Override
    public void onLoaderReset(Loader loader) {
        
    }
}
