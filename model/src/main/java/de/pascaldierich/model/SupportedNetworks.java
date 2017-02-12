package de.pascaldierich.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static de.pascaldierich.model.SupportedNetworks.GOOGLE_PLUS;
import static de.pascaldierich.model.SupportedNetworks.YOUTUBE;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Model Constants for supported social Networks
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        YOUTUBE,
        GOOGLE_PLUS
})
public @interface SupportedNetworks {

    /**
     * Name of supported Networks
     */
    public static final String YOUTUBE = "youtube";
    public static final String GOOGLE_PLUS = "plus";

}
