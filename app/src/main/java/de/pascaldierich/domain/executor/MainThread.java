package de.pascaldierich.domain.executor;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public interface MainThread {
    
    /**
     * Make runnable operation run in the main thread.
     *
     * @param runnable The runnable to run.
     */
    void post(final Runnable runnable);
}
