package de.pascaldierich.watchdog.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.main.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.fragments.main.Presenter;

public class ObservableListFragment extends Fragment implements ObservableListPresenter.View {
    private Presenter mPresenter;
    private Bundle mSavedInstanceState;

    private View mRootView;

    @Override
    public WeakReference<Context> getWeakContext() {
        return new WeakReference<Context>(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 27.02.17 set Layout
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_observable_list, container, false);

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter = Presenter.onStart(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                mSavedInstanceState, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.onResume();
    }

    /**
     * show initial Data
     */
    @Override
    public void setData() {

    }
}
