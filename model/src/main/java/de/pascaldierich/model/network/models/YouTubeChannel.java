package de.pascaldierich.model.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeChannel {

    @SerializedName("kind")
    private String mKind;

    @SerializedName("id")
    private int mId;

    public YouTubeChannel(String kind, int id) {
        mKind = kind;
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public int getId() {
        return mId;
    }
}
