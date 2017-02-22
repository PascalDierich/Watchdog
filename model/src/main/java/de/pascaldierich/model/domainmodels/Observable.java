package de.pascaldierich.model.domainmodels;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.graphics.Bitmap;

/**
 * POJO for representing observable-entries in 'Observables' table
 * @see {local.DOC_Watchdog.md}
 */
public class Observable {
    private int userId;
    private String displayName;
    private Bitmap thumbnail;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
