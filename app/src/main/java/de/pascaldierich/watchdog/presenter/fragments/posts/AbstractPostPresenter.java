package de.pascaldierich.watchdog.presenter.fragments.posts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.favorites.Set;
import de.pascaldierich.domain.interactors.storage.observable.Get;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;
import hugo.weaving.DebugLog;

abstract class AbstractPostPresenter extends AbstractPresenter {
    
    // Collection of all Posts in selected table ('NewsFeed' or 'Favorites')
    ArrayList<Post> mPosts;
    
    /**
     * @see {@link AbstractPresenter}
     */
    AbstractPostPresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
        mPosts = new ArrayList<Post>();
    }
    
    /**
     * @param selectedPage, boolean: true -> NewsFeed; false -> Favorites
     * @see {@link Get}
     */
    @DebugLog
    void getPosts(Context context, StorageInteractor.GetCallback presenter,
                            boolean selectedPage, int id) {
        if (selectedPage) {
            WeakReference<de.pascaldierich.domain.interactors.storage.newsfeed.Get>
                    wInteractor = new WeakReference<de.pascaldierich.domain.interactors.storage.newsfeed.Get>(
                    new de.pascaldierich.domain.interactors.storage.newsfeed.Get(
                            super.mExecutor,
                            super.mMainThread,
                            context,
                            presenter,
                            id));
            
            wInteractor.get().execute();
        } else {
            WeakReference<de.pascaldierich.domain.interactors.storage.favorites.Get>
                    wInteractor = new WeakReference<de.pascaldierich.domain.interactors.storage.favorites.Get>(
                    new de.pascaldierich.domain.interactors.storage.favorites.Get(
                            super.mExecutor,
                            super.mMainThread,
                            context,
                            presenter,
                            id));
            
            wInteractor.get().execute();
        }
    }
    
    /**
     * @see {@link Set}
     */
    @DebugLog
    void setFavorites(Context context, StorageInteractor.SetCallback presenter,
                                @NonNull Post post) {
        WeakReference<Set> wInteractor = new WeakReference<Set>(new Set(
                super.mExecutor,
                super.mMainThread,
                context,
                presenter,
                post
        ));
        
        wInteractor.get().execute();
    }
    
    
    protected void deleteFavorite() {
        // TODO: 28.02.17 implement delete-methods
    }
    
}
