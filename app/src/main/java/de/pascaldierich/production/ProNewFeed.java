package de.pascaldierich.production;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.interactors.storage.newsfeed.Set;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Post;

public final class ProNewFeed implements StorageInteractor.SetCallback {
    
    private static final String LOG_TAG = ProNewFeed.class.getSimpleName();
    
    int number = 0;
    
    public void addNewNewsFeedPosts(Executor e, MainThread m, Context c, int observableId) throws ModelException {
        
        // real bad...................
        
        
        WeakReference<Set> wInteractor = new WeakReference<Set>(new Set(
                 e, m, c, this, new Post().setUserId(observableId)
                .setDescription("Description " + ++number).setSite(SupportedNetworks.YOUTUBE)
                .setThumbnailUrl("lalala.jpg " + number).setTitle("Title here " + number)
                .setPostId("I'm unique XD " + number)));
        
        wInteractor.get().execute();
    }
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        Log.d(LOG_TAG, "onFailure: errorCode = " + errorCode);
    }
   
    @Override
    public void onSuccess(long id, boolean type) {
        Log.d(LOG_TAG, "onSuccess: id = " + id);
    }
}
