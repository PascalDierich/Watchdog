package de.pascaldierich.model.network.models.thumbnails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeSearchThumbnail {
    @SerializedName("high")
    @Expose
    private YouTubeSearchThumbnailHigh high;

    public YouTubeSearchThumbnailHigh getHigh() {
        return high;
    }

    public void setHigh(YouTubeSearchThumbnailHigh high) {
        this.high = high;
    }

}
