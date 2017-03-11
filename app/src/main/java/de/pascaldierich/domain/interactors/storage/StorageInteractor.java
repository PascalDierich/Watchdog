package de.pascaldierich.domain.interactors.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.interactors.base.BaseCallback;
import de.pascaldierich.domain.interactors.base.BaseInteractor;

/**
 * Storage Interactor
 */
public interface StorageInteractor extends BaseInteractor {
    
    /**
     * Set a new Context
     * <p>
     *
     * @param context, WeakReference<Context>: Context to access DB
     */
    void setContext(@NonNull Context context);
    
    /**
     * Storage-Callback for <b>get</b> Methods
     */
    interface GetCallback extends BaseCallback {
        
        /**
         * @param result, ArrayList<?>: Collection of queried data as POJO of 'domainmodels'
         */
        void onSuccess(@NonNull ArrayList<?> result);
        
    }
    
    /**
     * Storage-Callback for <b>set</b>Methods
     */
    interface SetCallback extends BaseCallback {
        
        /**
         * @param id, long: unique Id for specific entry
         */
        void onSuccess(long id, boolean type);
        
    }
}
