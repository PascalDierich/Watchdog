package de.pascaldierich.model.domainmodels;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * POJO for representing site-entries in 'Sites' table
 * @see {local.DOC_Watchdog.md}
 */
public class Site {
    private int userId;
    private String site;
    private String key;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
