package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.support.annotation.Nullable;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    /**
     * Check for id from user-input YouTube Channel name
     * <p/>
     *
     * @param active, boolean: true -> activated, false -> deactivated
     */
    void checkIdYouTube(boolean active);
    
    /**
     * Saves the current data as Observable-Site Objects in Model
     * <p>
     * <b>Note:</b>
     * an ArrayList<Site> is saved and updated
     * from beginning in Presenter.
     * <p/>
     *
     * @param displayName, String: user-input Observable Name
     */
    void onSaveClicked(String displayName);
    
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
         * returns the Text set in the YouTubeEditText
         * <p/>
         *
         * @return user-input, String: Channel name
         * @throws NullPointerException, if not usable input
         */
        String getTextYouTube() throws NullPointerException;
        
        /**
         * sets the YouTube-CheckBox
         * <p/>
         *
         * @param checked, boolean: indicates if checked or not
         */
        @Deprecated
        void setCheckBoxYouTube(boolean checked);
    }
    
    
}
