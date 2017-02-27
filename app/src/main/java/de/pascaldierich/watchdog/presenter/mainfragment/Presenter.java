package de.pascaldierich.watchdog.presenter.mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;

public class Presenter extends AbstractObservableListPresenter
        implements ObservableListPresenter, de.pascaldierich.domain.interactors.storage.StorageInteractor.GetCallback {

    private ObservableListPresenter.View mView;

    /*
        Instantiation
     */

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      ObservableListPresenter.View view) {
        super(executor, mainThread, savedInstance);

        mView = view;
    }

    public static Presenter onStart(Executor executor, MainThread mainThread, Bundle savedInstance,
                                    ObservableListPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }

    /**
     * onResume calls the super().getInitialData() method from AbstractXXXPresenter
     */
    @Override
    public void onResume() {
        super.getInitialData(mExecutor, mMainThread, mView.getWeakContext(), this);
    }

    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        // TODO: 27.02.17 define Error-Codes
        onError(-1);
    }

    /**
     * @param result
     */
    @Override
    public void onSuccess(@Nullable ArrayList<?> result) {
        // TODO: 27.02.17 Check result
        super.extractNewsNumbers(result);
    }

    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {

    }

    /**
     * @param observableId
     */
    @Override
    public void onObservableSelected(int observableId) {

    }

    /**
     * search in NewsFeedPosts for specific news from observableId (param).
     *
     * @param observableId
     */
    @Override
    public void searchNewsFeed(int observableId) {
        super.getNews(observableId);

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
