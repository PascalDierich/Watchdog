package de.pascaldierich.watchdog.presenter.mainfragment;

import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.newsfeed.Get;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;

public abstract class AbstractObservableListPresenter extends AbstractPresenter {
    private ArrayList<Post> mNewsFeed;

    protected AbstractObservableListPresenter(Executor executor, MainThread mainThread, Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
        mNewsFeed = new ArrayList<>();
    }


    protected void getInitialData(Executor executor, MainThread mainThread,
                                  WeakReference<Context> context, StorageInteractor.GetCallback presenter) {

        WeakReference<Get> wInteractor = new WeakReference<Get>(new Get(
                executor,
                mainThread,
                context,
                presenter
        ));

        wInteractor.get().run();
    }

    protected void extractNewsNumbers(ArrayList<?> newsFeed) {

    }

    protected void getNews(int observableId) {

    }
}
