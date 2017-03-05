package de.pascaldierich.watchdog.presenter.activities.main;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface MainPresenter extends BaseUIPresenter {
    
    void onClickFab();
    
    boolean getTwoPaneMode();
    
    interface View extends BaseView {
        
        /**
         * @return twoPaneMode, boolean:
         * true -> twoPaneMode active ( > sw600dp )
         * false -> twoPaneMode inactive ( < sw600dp )
         */
        boolean getUiMode();
        
        void setUiMode(boolean twoPaneMode);
        
        /**
         * starts the setObservable Fragment
         */
        void startSetObservableActivity(boolean twoPaneMode);
    }
    
}
