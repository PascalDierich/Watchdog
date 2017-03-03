package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.support.annotation.Nullable;

import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    /**
     * Gets called when switch state changes.
     *      checked = true -> check user-input and get Id
     *      checked = false -> delete (if exists) Site-Object from Collection.
     * Both states changes the TextColor for UX too.
     * <p/>
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param checked, boolean: true if checked, false if not
     */
    void onStateChanged(@SupportedNetworks String network, boolean checked);
    
    /**
     * Saves the current data as Observable-Site Objects in Model
     * <b>Note:</b>
     * an ArrayList<Site> is saved and updated in Presenter.
     * <p/>
     *
     * @param displayName, String: user-input Observable Name
     */
    void onSaveClicked(@Nullable String displayName);
    
    /**
     * Gets called when Network is activated but input got changed.
     * <p/>
     *
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param newInput, String: new User input to check
     */
    void onInputChanged(@SupportedNetworks String network, String newInput);
    
    interface View extends BaseView {
        
        /**
         * show given Observable and related Sites if exists
         * <p/>
         *
         * @param observable, Observable: existing Observable from db
         * @param sites,      Site[]: related Sites
         */
        void setData(@Nullable Observable observable, @Nullable Site[] sites);
        
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
         * change font-color of TextField for specific Site
         * <p/>
         * @param site, String: Site for which TextField the color get changed
         * @param color, int: colorCode
         */
        void setTextColor(@SupportedNetworks String site, int color);
    }
    
    
}
