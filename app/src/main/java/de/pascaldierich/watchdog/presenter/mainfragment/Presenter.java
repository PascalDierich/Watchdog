package de.pascaldierich.watchdog.presenter.mainfragment;

import android.os.Bundle;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;

public class Presenter extends AbstractPresenter implements MainFragmentPresenter {
    private static Presenter sInstance = null;

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance) {
        super(executor, mainThread, savedInstance);

    }

    private void setInstance(Executor executor, MainThread mainThread, Bundle savedInstance) {
        sInstance = new Presenter(executor, mainThread, savedInstance);
    }

    @Override
    public Presenter getInstance() {
        return sInstance;
    }

    @Override
    public void resume() {
        // call getData();
    }

    @Override
    public void start() {
        // call getInstance();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {

    }
}
