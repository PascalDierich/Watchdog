package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * TODO: write JAVADOC
 */
public class ModelException extends Exception{
    @ModelErrors.Codes
    private int errorCode;
    
    public ModelException(@ModelErrors.Codes int errorCode, @ModelErrors.Messages String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Returns errorCode and errorMessage
     *
     * @return errorCode + " " + super.getMessage().
     */
    @Override
    public String getMessage() {
        return errorCode + ", " + super.getMessage();
    }
}
