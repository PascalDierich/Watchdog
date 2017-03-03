package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface SetObservablePresenter extends BaseUIPresenter {
    
    void onSaveClicked(String displayName, @Nullable Bitmap thumbnail, ArrayList<Site> sites);

    interface View extends BaseView {

        /**
         * show given Observable and related Sites if exists
         * <p/>
         *
         * @param observable, Observable: existing Observable from db
         * @param sites, Site[]: related Sites
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
    }


}
