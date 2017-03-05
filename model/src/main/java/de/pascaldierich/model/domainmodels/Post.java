package de.pascaldierich.model.domainmodels;

import android.os.Parcel;
import android.os.Parcelable;

import de.pascaldierich.model.SupportedNetworks;

/**
 * POJO for representing post-entries
 * -> 'NewsFeed' table and 'Favorites' table
 * -> downloaded posts for domain-layer
 *
 * @see {local.DOC_Watchdog.md}
 * @see {local.WatchdogContract.class}
 */
public final class Post implements Parcelable {
    private boolean gotDownloaded;
    private int _ID; // _ID is only set if gotDownloaded = true.
    private int userId; // TODO: 27.02.17 rename one Id to observable Id
    private String thumbnailUrl;
    private String description;
    private String title;
    private String postId;
    @SupportedNetworks
    private String site;
    private String timestamp; // timestamp is only set if gotDownloaded = true.
    
    public Post() {
        
    }
    
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
    
    public int getUserId() {
        return userId;
    }
    
    public Post setUserId(int userId) {
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
    
    /*
        Parcelable
            -> created with http://www.parcelabler.com/
     */
    
    public Post(Parcel in) {
        gotDownloaded = in.readByte() != 0x00;
        _ID = in.readInt();
        userId = in.readInt();
        thumbnailUrl = in.readString();
        description = in.readString();
        title = in.readString();
        postId = in.readString();
        site = in.readString();
        timestamp = in.readString();
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (gotDownloaded ? 0x01 : 0x00));
        dest.writeInt(_ID);
        dest.writeInt(userId);
        dest.writeString(thumbnailUrl);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(postId);
        dest.writeString(site);
        dest.writeString(timestamp);
    }
    
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }
        
        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
