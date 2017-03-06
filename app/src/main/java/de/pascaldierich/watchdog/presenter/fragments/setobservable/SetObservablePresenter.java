package de.pascaldierich.watchdog.presenter.fragments.setobservable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    /**
     * Gets called when network-switch for state changes.
     *      checked = true -> check user-input and get Id
     *      checked = false -> delete (if exists) Site-Object from Collection.
     * Both states changes the TextColor for UX too.
     * <p/>
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param checked, boolean: true if checked, false if not
     */
    void onStateChanged(@SupportedNetworks String network, boolean checked);
    
    /**
     * Gets called when Network is activated but input got changed.
     * <p/>
     *
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param newInput, String: new User input to check
     */
    void onInputChanged(@SupportedNetworks String network, String newInput);
    
    /*
        Methods for SetObservableActivity.SetObservableCallback
     */
    
    /**
     * Checks if user-input is correct
     * <p/>
     * @return boolean, true -> input workable
     */
    boolean inputVerified();
    
    /**
     * returns the Observable set by User
     * <p/>
     * @return Observable, null
     */
    Observable getObservableCallback();
    
    /**
     * return the Site-Collection
     * <p/>
     * @return Site-Collection
     */
    ArrayList<Site> getSitesCallback();
    
    
    interface View extends BaseView {
        
        /**
         * show given Observable if exists
         * <p/>
         *
         * @param observable, Observable: existing Observable from db
         */
        void setObservable(@NonNull Observable observable);
    
        /**
         * show loaded Sites if Observable set
         * <p/>
         *
         * @param sites: ArrayList<Sites>: related Sites for set Observable
         */
        void setSites(@NonNull ArrayList<Site> sites);
        
        /**
         * shows an Error-Message
         * <p/>
         *
         * @param errorMessage, String
         */
        void showErrorMessage(String errorMessage);
        
        /**
         * changes the visibility of the progressBar
         */
        void changeProgressVisibility();
        
        /**
         * returns the Text set in the EditText for specific Network
         * <p/>
         *
         * @param network, String: Name of the Network for which the EditText works
         * @return user-input, String: Channel name
         * @throws NullPointerException, if not usable input
         */
        String getTextNetwork(@SupportedNetworks String network) throws NullPointerException;
    
        /**
         * return the Text from Name-Field
         * <p/>
         *
         * @return user-input, String: Name of Observable
         * @throws NullPointerException, if not usable input
         */
        String getTextDisplayName() throws NullPointerException;
    
        /**
         * change font-color of TextField for specific Site
         * <p/>
         *
         * @param site, String: Site for which TextField the color get changed
         * @param color, int: colorCode
         */
        void setTextColor(@SupportedNetworks String site, int color);
    
        /**
         * returns the args the fragment got created with.
         * Args contains (if != null) an Observable which should get edited.
         *      <b>Note:</b> key := R.string.parcelable_observable
         * <p/>
         *
         * @return args, Bundle: if Bundle != null it contains an Observable-Object
         */
        @Nullable
        Bundle getArgumentsBundle();
    }
}
