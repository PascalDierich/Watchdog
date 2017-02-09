package de.pascaldierich.model.network.models.pages;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import de.pascaldierich.model.network.models.YouTubeChannel;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeChannelsPage {

    @SerializedName("totalResults")
    private int mTotalResults;

    @SerializedName("items")
    private ArrayList<YouTubeChannel> mItems;

    public YouTubeChannelsPage(int totalResults, ArrayList<YouTubeChannel> items) {
        mTotalResults = totalResults;
        mItems = items;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public ArrayList<YouTubeChannel> getItems() {
        return mItems;
    }
}
