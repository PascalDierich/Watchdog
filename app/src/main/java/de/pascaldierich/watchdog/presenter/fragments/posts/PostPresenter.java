package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Intent;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

/**
 * Interface between PostFragment and related Presenter
 */
interface PostPresenter extends BaseUIPresenter {
    
    /**
     * @param selectedPage, boolean: true -> NewsFeed, false -> Favorites
     */
    void onPageChanged(boolean selectedPage);
    
    /**
     * saves given Post local on device
     * @param post, Post: item to save
     */
    void onSavePost(Post post);
    
    
    void setObservable(Observable observable);
    
    interface View extends BaseView {
        
        /**
         * show Posts (either NewsFeed or Favorites)
         */
        void setData(ArrayList<Post> posts);
        
        // TODO: change method signature and behaviour for twoPaneMode
        void startActivity(Intent intent);
    
        void sendIntentToActivity(Intent intent);
        
        /**
         * @return selectedPage, boolean: true -> NewsFeed; false -> Favorites
         */
        boolean getSelectedPage();
    }
    
}
