package de.pascaldierich.domain.interactors.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;

public class NewsFeedGet implements StorageInteractor {
    Context mContext;
    StorageInteractor.GetCallback mCallback;
    int mObservableId;
    
    public NewsFeedGet(@NonNull Context context, @NonNull StorageInteractor.GetCallback callback,
                       int observableId) {
        mContext = context;
        mCallback = callback;
        mObservableId = observableId;
    }
    
    @Override
    public void execute() {
        try {
            ArrayList<Post> result = ApiConnector.getApi().get().getNewsFeed(mContext, mObservableId);
            mCallback.onSuccess(result);
            
        } catch (ModelException modelE) {
            // TODO: 10.03.17 define Error-Routine
        }
    }
    
    
    @Override
    public void setContext(@NonNull Context context) {
        
    }
}
