package de.pascaldierich.model;

/**
 * Exception as Error-Interface for 'app' - 'model'
 */
public class ModelException extends Exception {
    @ModelErrorsCodes
    private int errorCode = ModelErrorsCodes.UNKNOWN_FATAL_ERROR;
    
    public ModelException(@ModelErrorsCodes int errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    /**
     * Returns errorCode
     *
     * @return errorCode, int: @ModelErrorCodes
     */
    @ModelErrorsCodes
    public int getErrorCode() {
        return errorCode;
    }
}
