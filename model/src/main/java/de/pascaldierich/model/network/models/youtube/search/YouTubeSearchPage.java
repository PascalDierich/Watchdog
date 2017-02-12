package de.pascaldierich.model.network.models.youtube.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import de.pascaldierich.model.network.models.youtube.YouTubePageInfo;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeSearchPage {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("pageInfo")
    @Expose
    private YouTubePageInfo pageInfo;
    @SerializedName("items")
    @Expose
    private ArrayList<YouTubeSearchItem> items = null;

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

    public YouTubePageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(YouTubePageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public ArrayList<YouTubeSearchItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<YouTubeSearchItem> items) {
        this.items = items;
    }
}

