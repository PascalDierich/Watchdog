package de.pascaldierich.domain.interactors.base;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Main methods for each Interactor
 */
public interface BaseInteractor {

    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();

}
