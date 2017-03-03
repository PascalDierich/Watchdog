package de.pascaldierich.model.domainmodels;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * POJO for representing observable-entries in 'Observables' table
 *
 * @see {local.DOC_Watchdog.md}
 */
public final class Observable {
    private boolean gotThumbnail;
    private int userId;
    private String displayName;
    private byte[] thumbnail;
    
    public boolean getGotThumbnail() {
        return gotThumbnail;
    }
    
    public Observable setGotThumbnail(boolean gotThumbnail) {
        this.gotThumbnail = gotThumbnail;
        return this;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public Observable setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public Observable setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
    
    public byte[] getThumbnail() {
        return thumbnail;
    }
    
    public Observable setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
