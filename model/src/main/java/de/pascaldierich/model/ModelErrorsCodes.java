package de.pascaldierich.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.pascaldierich.model.ModelErrorsCodes.Converter.PARAMETER_EMPTY;
import static de.pascaldierich.model.ModelErrorsCodes.Converter.PARAMETER_NULL;
import static de.pascaldierich.model.ModelErrorsCodes.Storage.INSERT_FAILED;
import static de.pascaldierich.model.ModelErrorsCodes.Storage.REMOVE_FAILED;
import static de.pascaldierich.model.ModelErrorsCodes.Storage.UNKNOWN_URI;
import static de.pascaldierich.model.ModelErrorsCodes.UNKNOWN_FATAL_ERROR;

/**
 * Error-Codes for communication between app - model
 * <p>
 * Get transmitted by ModelException.class
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        UNKNOWN_FATAL_ERROR
})
public @interface ModelErrorsCodes {
    int UNKNOWN_FATAL_ERROR = 10;
    
    /**
     * TODO: get normal http-Code from Retrofit as ErrorCode
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Network.RUNTIME_ERROR,
            Network.IO_ERROR
    })
    @interface Network {
        int RUNTIME_ERROR = 100;
        int IO_ERROR = 101;
    }
    
    /**
     *
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Converter.RUNTIME_ERROR,
            PARAMETER_NULL,
            PARAMETER_EMPTY
    })
    @interface Converter {
        int RUNTIME_ERROR = 200;
        int PARAMETER_NULL = 201;
        int PARAMETER_EMPTY = 202;
    }
    
    /**
     *
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Storage.RUNTIME_ERROR,
            UNKNOWN_URI,
            INSERT_FAILED,
            REMOVE_FAILED
    })
    @interface Storage {
        int RUNTIME_ERROR = 300;
        int UNKNOWN_URI = 301;
        int INSERT_FAILED = 302;
        int REMOVE_FAILED = 303;
    }
    
}
