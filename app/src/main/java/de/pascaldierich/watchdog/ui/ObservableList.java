package de.pascaldierich.watchdog.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.presenter.mainfragment.ObservableListPresenter;
import de.pascaldierich.watchdog.presenter.mainfragment.Presenter;

public class ObservableList extends Fragment implements ObservableListPresenter.View {
    private Presenter mPresenter;
    private Bundle mSavedInstanceState;

    @Override
    public WeakReference<Context> getWeakContext() {
        return new WeakReference<Context>(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
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
