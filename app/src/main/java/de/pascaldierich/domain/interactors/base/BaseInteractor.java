package de.pascaldierich.domain.interactors.base;

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
