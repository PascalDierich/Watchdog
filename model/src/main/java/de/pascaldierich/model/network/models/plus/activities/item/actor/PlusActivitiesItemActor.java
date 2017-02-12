package de.pascaldierich.model.network.models.plus.activities.item.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItemActor {
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
    private PlusActivitiesItemActorImage image;

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

    public PlusActivitiesItemActorImage getImage() {
        return image;
    }

    public void setImage(PlusActivitiesItemActorImage image) {
        this.image = image;
    }
}
