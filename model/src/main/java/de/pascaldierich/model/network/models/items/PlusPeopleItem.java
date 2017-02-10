package de.pascaldierich.model.network.models.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.pascaldierich.model.network.models.thumbnails.PlusPeopleThumbnail;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusPeopleItem {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("objectType")
    @Expose
    private String objectType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private PlusPeopleThumbnail image;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PlusPeopleThumbnail getImage() {
        return image;
    }

    public void setImage(PlusPeopleThumbnail image) {
        this.image = image;
    }

}
