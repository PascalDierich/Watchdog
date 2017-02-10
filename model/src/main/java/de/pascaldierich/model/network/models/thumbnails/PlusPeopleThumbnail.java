package de.pascaldierich.model.network.models.thumbnails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusPeopleThumbnail {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
