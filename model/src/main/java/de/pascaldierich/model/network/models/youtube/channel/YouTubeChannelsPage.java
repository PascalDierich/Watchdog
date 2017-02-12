package de.pascaldierich.model.network.models.youtube.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import de.pascaldierich.model.network.models.youtube.YouTubePageInfo;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeChannelsPage {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("pageInfo")
    @Expose
    private YouTubePageInfo mYouTubePageInfo;
    @SerializedName("items")
    @Expose
    private ArrayList<YouTubeChannelItem> items = null;

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

    public YouTubePageInfo getPageInfo() {
        return mYouTubePageInfo;
    }

    public void setPageInfo(YouTubePageInfo YouTubePageInfo) {
        this.mYouTubePageInfo = YouTubePageInfo;
    }

    public ArrayList<YouTubeChannelItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<YouTubeChannelItem> items) {
        this.items = items;
    }
}
