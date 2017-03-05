package de.pascaldierich.watchdog.presenter.activities.setobservable;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    /**
     *
     */
    void onSaveClicked();
    
    interface View extends BaseView {
    
        /**
         * start the MainActivity.
         * gets called by Presenter.onSaveClicked().
         */
        void startMainActivity();
    
        /**
         *
         * @param fragment
         */
        void setFragment(SetObservableFragment fragment);
    }
}
