package de.pascaldierich.model.domainmodels;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * POJO for representing site-entries in 'Sites' table
 *
 * @see {local.DOC_Watchdog.md}
 */
public final class Site {
    private boolean gotDownloaded;
    private int userId;
    private String site;
    private String key;
    
    public boolean getGotDownloaded() {
        return gotDownloaded;
    }
    
    public Site setGotDownloaded(boolean gotDownloaded) {
        this.gotDownloaded = gotDownloaded;
        return this;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public Site setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    
    public String getSite() {
        return site;
    }
    
    public Site setSite(String site) {
        this.site = site;
        return this;
    }
    
    public String getKey() {
        return key;
    }
    
    public Site setKey(String key) {
        this.key = key;
        return this;
    }
}
