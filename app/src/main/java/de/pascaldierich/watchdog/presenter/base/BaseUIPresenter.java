package de.pascaldierich.watchdog.presenter.base;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Basic Presenter methods for Lifecycle events and Error messages
 */
public interface BaseUIPresenter {

    /**
     * in onCreate the Singleton-Presenter is going to be created
     * @param executor
     * @param mainThread
     * @param savedInstance
     */
//    void onCreate(Executor executor, MainThread mainThread, Bundle savedInstance);

    /**
     * onStart returns a Presenter-Instance
     * @param <T>
     * @return
     */
//    <T extends AbstractPresenter> T onStart();

    /**
     * onResume calls the super().getInitialData() method from AbstractXXXPresenter
     */
    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    /**
     * @param errorCode
     */
    void onError(@ErrorPresenter int errorCode);

}
