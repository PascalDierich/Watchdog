package de.pascaldierich.watchdog.presenter.base;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Basic Presenter methods for Lifecycle events and Error messages
 */
public interface BaseUIPresenter {

    void resume();

    void start();

    void pause();

    void stop();

    void destroy();

    /**
     * @param errorCode
     */
    void onError(@ErrorPresenter int errorCode);

}
