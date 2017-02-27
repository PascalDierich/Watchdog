package de.pascaldierich.watchdog.presenter.mainfragment;

import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseView;

public interface ObservableListPresenter extends BaseUIPresenter {
    /**
     * search in NewsFeedPosts for specific news from observableId (param).
     * @param observableId
     */
    void searchNewsFeed(int observableId);

    /**
     *
     * @param observableId
     */
    void onObservableSelected(int observableId);

    interface View extends BaseView {

        /**
         * show initial Data
         */
        void setData();



        void startNewsFeed();

    }

}
