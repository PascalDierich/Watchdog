package de.pascaldierich.watchdog.presenter.activities.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface MainPresenter extends BaseUIPresenter {
    
    void onClickFab();
    
    boolean getTwoPaneMode();
    
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
         * @param observable, Observable: Observable to change
         */
        void startSetObservableFragment(@Nullable Observable observable);
    
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
         * explicit intent to start
         * <p/>
         * @param intent, Intent
         */
        void startExplicitIntent(@NonNull Intent intent);
        
    }
    
}

/**
 * Presenter implements 3 Callback-classes:
 *
 *      - ObservableListFragment.Callback
 *          -> onObservableSelected(Observable observable)
 *              --> onePaneMode = start PostActivity
 *              --> twoPaneMode = update PostFragment
 *          -> onObservableSetting(Observable observable)
 *              --> onePaneMode = start SetObservableActivity
 *              --> twoPaneMode = replace SetObservableFragment with PostFragment
 *
 *      - PostFragment.Callback
 *          -> onPostSelected(Post post)
 *              --> onePaneMode = start explicit Intent
 *              --> twoPaneMode =  ``    ``      ``
 *
 *      - SetObservableFragment.Callback
 *          -> TODO: Problem: Save button is in Activity. -> maybe in save in static holder?
 *
 */
