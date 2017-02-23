package de.pascaldierich.domain.repository;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import java.lang.ref.WeakReference;

import de.pascaldierich.model.Model;

/**
 * ApiConnector
 * <p>
 * Provides Model-Instance with WeakReference
 */
public abstract class ApiConnector {
    public static WeakReference<Model> getApi() {
        return Model.getInstance();
    }
}