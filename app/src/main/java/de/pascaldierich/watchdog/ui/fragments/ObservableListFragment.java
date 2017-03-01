package de.pascaldierich.watchdog.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.main.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.fragments.main.Presenter;
import de.pascaldierich.watchdog.ui.adapter.ObservablesContainerAdapter;
import hugo.weaving.DebugLog;

public class ObservableListFragment extends Fragment implements ObservableListPresenter.View {
    private Presenter mPresenter;
    private ObservablesContainerAdapter mAdapter;

    private View mRootView;

    @DebugLog
    @Override
    public WeakReference<Context> getWeakContext() {
        return new WeakReference<Context>(getContext());
    }

    @Override
    public void showError() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this.getActivity());

        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstanceState, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_observable_list, container, false);

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
     *
     * @param observables
     */
    @Override
    public void setData(ArrayList<Observable> observables) {


    }
}
