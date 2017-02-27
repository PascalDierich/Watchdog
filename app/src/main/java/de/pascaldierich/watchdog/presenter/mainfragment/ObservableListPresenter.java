package de.pascaldierich.watchdog.presenter.mainfragment;

import android.content.Intent;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface ObservableListPresenter extends BaseUIPresenter {
    /**
     * called by onClickListener for CardView
     *
     * @param observableId, int: unique Observables identifier
     */
    void onObservableSelected(int observableId);

    interface View extends BaseView {

        /**
         * show Observables
         */
        void setData(ArrayList<Observable> observables);

        void startActivity(Intent intent);

    }

}
