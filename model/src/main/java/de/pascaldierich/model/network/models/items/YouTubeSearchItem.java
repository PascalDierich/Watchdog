package de.pascaldierich.model.network.models.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.pascaldierich.model.network.models.YouTubeSearchId;
import de.pascaldierich.model.network.models.YouTubeSearchSnippet;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeSearchItem {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private YouTubeSearchId id;
    @SerializedName("snippet")
    @Expose
    private YouTubeSearchSnippet snippet;

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

    public YouTubeSearchId getId() {
        return id;
    }

    public void setId(YouTubeSearchId id) {
        this.id = id;
    }

    public YouTubeSearchSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(YouTubeSearchSnippet snippet) {
        this.snippet = snippet;
    }



}
