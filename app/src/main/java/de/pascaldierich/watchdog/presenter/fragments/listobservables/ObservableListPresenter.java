package de.pascaldierich.watchdog.presenter.fragments.listobservables;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface ObservableListPresenter extends BaseUIPresenter {
    
    interface View extends BaseView {
        
        /**
         * show Observables
         */
        void setData(ArrayList<Observable> observables);
    
        /**
         * send the Observable to Callback (MainActivity XOR PostsActivity)
         * gets fired each time at CardView onClick event and
         * at launch by default.
         * <p/>
         *
         * @param observable, Observable: chosen Observable to transmit
         * @param defaultArg, boolean: true -> default start of method
         *                             false -> start of method because of active user-interaction
         */
        void sendObservableToCallback(@NonNull Observable observable, boolean defaultArg);
        
    }
    
}
