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
         * TODO: write DOC
         * @param observable
         */
        void sendObservableToMain(@NonNull Observable observable);
        
    }
    
}
