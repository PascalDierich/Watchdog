package de.pascaldierich.domain.repository;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import java.lang.ref.WeakReference;

import de.pascaldierich.model.Model;

/**
 * ApiConnector
 *
 * Provides Model-Instance with WeakReference
 */
public class ApiConnector {
    public static WeakReference<Model> getApi() {
        return Model.getInstance();
    }
}