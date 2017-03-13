package de.pascaldierich.watchdog.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.listobservables.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.fragments.listobservables.Presenter;
import de.pascaldierich.watchdog.ui.adapter.ObservablesContainerAdapter;

/**
 * Fragment for MainActivity.
 * Presents the List of Observables
 */
public class ObservableListFragment extends Fragment implements ObservableListPresenter.View {
    private static final String LOG_TAG = ObservableListFragment.class.getSimpleName();
    
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
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_observable_list, container, false);
        ButterKnife.bind(this, mRootView);
        
        mObservablesContainer.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mObservablesContainer.setAdapter(mAdapter);
        
        return mRootView;
    }
    
    // TODO: 06.03.17 Deprecated!
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
}
