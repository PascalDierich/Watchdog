package de.pascaldierich.model;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.pascaldierich.model.ModelErrors.Codes.Converter.PARAMETER_EMPTY;
import static de.pascaldierich.model.ModelErrors.Codes.Converter.PARAMETER_NULL;
import static de.pascaldierich.model.ModelErrors.Codes.Converter.RUNTIME_ERROR;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Error-Codes for communication between app - model
 *
 * Get transmitted by ModelException.class
 */
public interface ModelErrors {

    @interface Codes {
        /**
         * TODO: get normal http-Code from Retrofit as ErrorCode
         */
        @Retention(RetentionPolicy.SOURCE)
        @IntDef({
        })
        @interface Network {

        }

        /**
         *
         */
        @Retention(RetentionPolicy.SOURCE)
        @IntDef({
                RUNTIME_ERROR,
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
        })
        @interface Storage {

        }
    }

    @interface Messages {
        /**
         * TODO: get normal http-Code from Retrofit as ErrorCode
         */
        @Retention(RetentionPolicy.SOURCE)
        @StringDef({
        })
        @interface Network {

        }

        /**
         *
         */
        @Retention(RetentionPolicy.SOURCE)
        @StringDef({
        })
        @interface Converter {
        }

        /**
         *
         */
        @Retention(RetentionPolicy.SOURCE)
        @StringDef({
        })
        @interface Storage {

        }
    }
}
