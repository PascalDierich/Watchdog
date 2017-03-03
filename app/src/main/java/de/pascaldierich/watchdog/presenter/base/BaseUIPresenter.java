package de.pascaldierich.watchdog.presenter.base;

/**
 * Basic Presenter methods for Lifecycle events and Error messages
 */
public interface BaseUIPresenter {
    
    /**
     * onStart is used to get initialData.
     */
    void onStart();
    
    void onResume();
    
    void onPause();
    
    void onStop();
    
    void onDestroy();
    
    /**
     * @param errorCode
     */
    void onError(@ErrorPresenter int errorCode);
    
}
