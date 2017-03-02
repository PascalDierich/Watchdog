package de.pascaldierich.watchdog.presenter.activities.main;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface MainPresenter extends BaseUIPresenter {

    void onClickFab();

    interface View extends BaseView {

        /**
         * @return twoPaneMode, boolean:
         *      true -> twoPaneMode active ( > sw600dp )
         *      false -> twoPaneMode inactive ( < sw600dp )
         */
        boolean getUiMode();

        void setUiMode(boolean twoPaneMode);

        /**
         * shows the setObservable Fragment
         */
        void startSetObservableFragment();
    }

}
