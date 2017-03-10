package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

/**
 * Interface between PostFragment and related Presenter
 */
public interface PostPresenter extends BaseUIPresenter {
    
    /**
     * @param selectedPage, boolean: true -> NewsFeed, false -> Favorites
     */
    void onPageChanged(boolean selectedPage);
    
    /**
     * saves given Post local on device
     * @param post, Post: item to save
     */
    void onSavePost(Post post);
    
    interface View extends BaseView {
    
        /**
         * returns the args the fragment got created with.
         *      <b>Note:</b> key := R.string.parcelable_observable
         * <p/>
         *
         * @return args, Bundle: contains an Observable-Object
         */
        @NonNull
        Bundle getArgumentsBundle();
        
        /**
         * show Posts (either NewsFeed or Favorites)
         */
        void setData(ArrayList<Post> posts);
    
        void setErrorMessage(String message);
    
        void sendIntentToActivity(Intent intent);
        
        /**
         * @return selectedPage, boolean: true -> NewsFeed; false -> Favorites
         */
        boolean getSelectedPage();
    
        /**
         * shows the current Observable in included default layout.
         * <p/>
         *
         * @param observable, Observable
         */
        void showObservable(Observable observable);
    }
    
}
