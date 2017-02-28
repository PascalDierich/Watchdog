package de.pascaldierich.domain.interactors.network;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.interactors.base.BaseCallback;
import de.pascaldierich.domain.interactors.base.BaseInteractor;
import de.pascaldierich.model.domainmodels.Site;

public interface GetIdInteractor extends BaseInteractor {

    /**
     * Set a new Name
     * <p>
     *
     * @param name, String: Name to search for
     */
    void setName(@NonNull String name);

    interface GetIdCallback extends BaseCallback {

        /**
         * @param result, ArrayList<Site>: Collection of Sites
         */
        void onSuccess(@NonNull ArrayList<Site> result);
    }
}
