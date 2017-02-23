package de.pascaldierich.domain.interactors.storage.observable;

import de.pascaldierich.domain.interactors.base.BaseCallback;
import de.pascaldierich.domain.interactors.base.BaseInteractor;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Interactor for adding a new 'Observable' to intern storage
 */
public interface AddNewInteractor extends BaseInteractor {

    interface Callback extends BaseCallback {

        void onSuccess();

    }

}
