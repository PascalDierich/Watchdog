package de.pascaldierich.domain.interactors.storage.newsfeed;

import de.pascaldierich.domain.interactors.base.BaseCallback;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Interactor for adding a new 'Post' to 'NewsFeed' intern storage
 */
public interface AddNewInteractor {

    interface Callback extends BaseCallback {

        void onSuccess();

    }

}
