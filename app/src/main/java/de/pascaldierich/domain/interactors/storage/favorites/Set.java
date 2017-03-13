package de.pascaldierich.domain.interactors.storage.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.crash.FirebaseCrash;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.storage.Storage;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;

/**
 * Interactor to save a new Post in 'Favorites'
 */
public class Set extends Storage implements StorageInteractor {
    private StorageInteractor.SetCallback mCallback;
    private Post mItem;
    
    /**
     * Constructor
     * <p>
     *
     * @param executor,   Executor:
     * @param mainThread, MainThread:
     * @param callback,   AddNewInteractor.Callback: usually represented by 'this'
     * @param context,    WeakReference<Context>: Context to access DB
     * @param item,       Site: new Site to store
     */
    public Set(@NonNull Executor executor, @NonNull MainThread mainThread,
               @NonNull Context context,
               @NonNull StorageInteractor.SetCallback callback,
               @Nullable Post item) {
        super(executor, mainThread, context);
        
        mCallback = callback;
        mItem = item;
    }
    
    /**
     * Set a new Post
     * <p>
     *
     * @param item, Post: new Post to store
     */
    public void setItem(@NonNull Post item) {
        mItem = item;
    }
    
    /**
     * run Interactor
     */
    @Override
    public void run() {
        try {
            if (mItem == null) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseCrash.log("setFavorites: Item is null. \n" +
                                "postFailure in " + Set.class.getSimpleName());
                        
                        mCallback.onFailure(-1);
                    }
                });
            }
            
            ApiConnector.getApi().get().setFavorite(mContext, mItem);
            
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess(0, true);
                }
            });
        } catch (final ModelException modelE) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    FirebaseCrash.log("setFavorites: " + modelE.getErrorCode() + ". \n" +
                            "ModelException in " + Set.class.getSimpleName());
                    
                    mCallback.onFailure(modelE.getErrorCode());
                }
            });
        }
    }
}
