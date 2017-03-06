package de.pascaldierich.watchdog.presenter.activities.posts;

import android.content.Intent;
import android.support.annotation.NonNull;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;
import de.pascaldierich.watchdog.ui.fragments.PostsFragment;

public interface PostsPresenter extends BaseUIPresenter {
    
    
    interface View extends BaseView {
    
        /**
         * Replaces the container-placeholder with SetObservableFragment.class
         * <p/>
         * @param fragment, PostsFragment: Fragment which may contains args
         */
        void setFragment(PostsFragment fragment);
    
        /**
         * returns this.getIntent to get Observable-Parameter
         * <p/>
         *      key = key = R.string.parcelable_observable
         *
         * @return intent, getIntent();
         */
        @NonNull
        Intent getIntentExtra();
    
        /**
         * implicit intent to start
         * <p/>
         *
         * @param intent
         */
        void startImplicitIntent(@NonNull Intent intent); // TODO: 06.03.17 define Callback
        
    }
}
