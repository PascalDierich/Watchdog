package de.pascaldierich.model.network.models.plus.activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import de.pascaldierich.model.network.models.plus.activities.item.PlusActivitiesItem;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesPage {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("selfLink")
    @Expose
    private String selfLink;
    @SerializedName("nextLink")
    @Expose
    private String nextLink;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("items")
    @Expose
    private ArrayList<PlusActivitiesItem> items = null;

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

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<PlusActivitiesItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PlusActivitiesItem> items) {
        this.items = items;
    }

}
