package de.pascaldierich.model.domainmodels;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import de.pascaldierich.model.SupportedNetworks;

/**
 * POJO for representing post-entries
 *      -> 'NewsFeed' table and 'Favorites' table
 *      -> downloaded posts for domain-layer
 *
 * @see {local.DOC_Watchdog.md}
 * @see {local.WatchdogContract.class}
 */
public class Post {
    private boolean gotDownloaded;
    private int _ID; // _ID is only set if gotDownloaded = true.
    private String userId;
    private String thumbnailUrl;
    private String description;
    private String title;
    private String postId;
    @SupportedNetworks
    private String site;
    private String timestamp; // timestamp is only set if gotDownloaded = true.

    public boolean getGotDownloaded() {
        return gotDownloaded;
    }

    public Post setGotDownloaded(boolean gotDownloaded) {
        this.gotDownloaded = gotDownloaded;
        return this;
    }

    public int get_ID() {
        return _ID;
    }

    public Post set_ID(int _ID) {
        this._ID = _ID;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Post setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Post setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPostId() {
        return postId;
    }

    public Post setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getSite() {
        return site;
    }

    public Post setSite(@SupportedNetworks String site) {
        this.site = site;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Post setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
