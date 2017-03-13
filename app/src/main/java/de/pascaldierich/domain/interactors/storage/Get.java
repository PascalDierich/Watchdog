package de.pascaldierich.domain.interactors.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.threading.MainThreadImpl;

public class Get implements StorageInteractor {
    Context mContext;
    StorageInteractor.GetCallback mCallback;
    
    public Get(@NonNull Context context, @NonNull StorageInteractor.GetCallback callback) {
        mContext = context;
        mCallback = callback;
    }
    
    public void getNewsFeed(int observableId) {
        try {
            if (observableId < 1) {
                mCallback.onSuccess(ApiConnector.getApi().get().getNewsFeed(mContext));
            } else {
                mCallback.onSuccess(ApiConnector.getApi().get().getNewsFeed(mContext, observableId));
            }
        } catch (ModelException modelE) {
            mCallback.onFailure(modelE.getErrorCode());
            // TODO: 10.03.17 define Error-Routine
        }
    }
    
    public void getNewsFeed() {
        MainThreadImpl.getInstance().post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCallback.onSuccess(ApiConnector.getApi().get().getNewsFeed(mContext));
                } catch (final ModelException modelE) {
                    mCallback.onFailure(modelE.getErrorCode());
                }
            }
        });
    }
    
    public void getSites(int observableId) {
        try {
            if (observableId < 1) {
                mCallback.onSuccess(ApiConnector.getApi().get().getSites(mContext));
            } else {
                mCallback.onSuccess(ApiConnector.getApi().get().getSites(mContext, observableId));
            }
        } catch (ModelException modelE) {
            mCallback.onFailure(modelE.getErrorCode());
            // TODO: 10.03.17 define Error-Routine
        }
    }
    
    public void getFavorites(int observableId) {
        try {
            if (observableId < 1) {
                mCallback.onSuccess(ApiConnector.getApi().get().getFavorites(mContext));
            } else {
                mCallback.onSuccess(ApiConnector.getApi().get().getFavorites(mContext, observableId));
            }
        } catch (ModelException modelE) {
            mCallback.onFailure(modelE.getErrorCode());
            // TODO: 10.03.17 define Error-Routine
        }
    }
    
    public void getObservable() {
        try {
            mCallback.onSuccess(ApiConnector.getApi().get().getObservables(mContext));
        } catch (ModelException modelE) {
            mCallback.onFailure(modelE.getErrorCode());
            // TODO: 10.03.17 define Error-Routine
        }
    }
    
    @Override
    public void execute() {
        
    }
    
    
    @Override
    public void setContext(@NonNull Context context) {
        mContext = context;
    }
}
