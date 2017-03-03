package de.pascaldierich.watchdog.presenter.fragments.main;

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
        
        // TODO: change method signature and behaviour for twoPaneMode
        void startActivity(Intent intent);
        
    }
    
}
