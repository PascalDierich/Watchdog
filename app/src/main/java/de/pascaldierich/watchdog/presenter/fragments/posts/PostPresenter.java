package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Intent;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface PostPresenter extends BaseUIPresenter {
    
    void onPageChanged(boolean selectedPage);
    
    void onSavePost(Post post);
    
    interface View extends BaseView {
        
        /**
         * show Posts (either NewsFeed or Favorites)
         */
        void setData(ArrayList<Post> posts);
        
        // TODO: change method signature and behaviour for twoPaneMode
        void startActivity(Intent intent);
        
        /**
         * @return selectedPage, boolean: true -> NewsFeed; false -> Favorites
         */
        boolean getSelectedPage();
    }
    
}
