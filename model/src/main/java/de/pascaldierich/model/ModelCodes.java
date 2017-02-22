package de.pascaldierich.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Codes for communication between app - model
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({

})
public @interface ModelCodes {

    /********************************************************************************************
     * Network Codes:
     *
     ********************************************************************************************/



    /********************************************************************************************
     * Storage Codes:
     *
     * Storage Codes are needed for running specific SQL-Statements,
     * because 'app' has no authorization to access storage Constants.
     ********************************************************************************************/










}
