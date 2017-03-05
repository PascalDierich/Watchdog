package de.pascaldierich.watchdog.presenter.activities.setobservable;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    /**
     * gets data from Fragment and saves to storage.
     * if success, starts MainActivity.class
     */
    void onSaveClicked();
    
    interface View extends BaseView {
    
        /**
         * start the MainActivity.
         * gets called by Presenter.onSaveClicked().
         */
        void startMainActivity();
    
        /**
         * Replaces the container-placeholder with SetObservableFragment.class
         * @param fragment
         */
        void setFragment(SetObservableFragment fragment);
    }
}
