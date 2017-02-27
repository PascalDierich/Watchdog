package de.pascaldierich.watchdog.presenter.mainfragment;

import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import de.pascaldierich.watchdog.presenter.base.BaseUIPresenter;

public interface MainFragmentPresenter extends BaseUIPresenter {

    /**
     * implement Presenter as Singleton
     */
    <T extends AbstractPresenter> T getInstance();

    /**
     * calls Interactor to get NewsFeed Posts
     */
    void getData();

    /**
     * convert Data to be UI-friendly
     */
    void extractData();

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

    interface View {

        /**
         * show initial Data
         */
        void setData();



        void startNewsFeed();

    }

}
