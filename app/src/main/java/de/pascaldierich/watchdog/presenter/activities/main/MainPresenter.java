package de.pascaldierich.watchdog.presenter.activities.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

public interface MainPresenter extends BaseUIPresenter {
    
    void onClickFab();
    
    /**
     * Method to start PostFragment || PostActivity
     * <p/>
     *
     * @param item, Observable: selected Observable to transmit
     * @param defaultArg, boolean: indicates if PostActivity should launch
     */
    void onObservableSelected(@NonNull Observable item, boolean defaultArg);
    
    interface View extends BaseView {
        
        /*
            initial methods
         */
    
        /**
         * @return twoPaneMode, boolean:
         * true -> twoPaneMode active ( > sw600dp )
         * false -> twoPaneMode inactive ( < sw600dp )
         */
        boolean getUiMode();
        
        void setUiMode(boolean twoPaneMode);
    
    
        /*
            Methods to start/update fragments/activities
         */
    
        /**
         * starts the setObservable Activity
         *      -> twoPaneMode = false
         * <p/>
         * @param observable, Observable: Observable to change
         */
        void startSetObservableActivity(@Nullable Observable observable);
    
        /**
         * starts the setObservable Fragment
         *      -> twoPaneMode = true
         * <p/>
         * @param fragment, SetObservableFragment: Fragment to set inside fragment_container
         * @param observable, Observable: Observable to change
         */
        void startSetObservableFragment(@NonNull SetObservableFragment fragment, @Nullable Observable observable);
    
        /**
         * starts the Posts Activity
         *      -> twoPaneMode = false
         * <p/>
         * @param observable, Observable: selected Observable-Item
         */
        void startPostsActivity(@NonNull Observable observable);
    
        /**
         * updates the Posts Fragment
         *      -> twoPaneMode = true
         * <p/>
         * @param observable, Observable: selected Observable-Item
         */
        void updatePostsFragment(@NonNull Observable observable);
    
        /**
         * implicit intent to start
         * <p/>
         * @param intent, Intent
         */
        void startImplicitIntent(@NonNull Intent intent);
    
        boolean getGotInstanceState();
        
        SetObservableFragment getFragment();
        
    }
    
}
