package de.pascaldierich.domain.interactors.storage.site;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import de.pascaldierich.domain.interactors.base.BaseCallback;
import de.pascaldierich.domain.interactors.base.BaseInteractor;

/**
 * Interactor for adding a new 'Site' to intern storage
 */
public interface AddNewInteractor extends BaseInteractor {

    interface Callback extends BaseCallback {

        void onSuccess();

    }

}
