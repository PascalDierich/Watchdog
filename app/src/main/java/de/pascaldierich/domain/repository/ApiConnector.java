package de.pascaldierich.domain.repository;

import java.lang.ref.WeakReference;

import de.pascaldierich.model.Model;
import hugo.weaving.DebugLog;

/**
 * ApiConnector
 * <p>
 * Provides Model-Instance with WeakReference
 */
@DebugLog
public abstract class ApiConnector {
    public static WeakReference<Model> getApi() {
        return new WeakReference<Model>(Model.getInstance());
    }
    
    // Test method
    @Deprecated
    public static Model getApiTest() {
        return Model.getInstance();
    }
    
}