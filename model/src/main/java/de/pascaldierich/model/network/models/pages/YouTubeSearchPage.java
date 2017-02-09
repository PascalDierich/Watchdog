package de.pascaldierich.model.network.models.pages;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import de.pascaldierich.model.network.models.YouTubeSearch;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeSearchPage {

    // header
    @SerializedName("totalResults")
    private int mTotalResults; // TODO: 09.02.17 totalResults is in pageInfo Object -> not sure if works -> see DOC.md

    // body
    @SerializedName("items")
    private ArrayList<YouTubeSearch> mItems;
    
    public YouTubeSearchPage(int totalResults, ArrayList<YouTubeSearch> items) {
        mTotalResults = totalResults;
        mItems = items;
    }
    
    public int getTotalResults() {
        return mTotalResults;
    }
    
    public ArrayList<YouTubeSearch> getItems() {
        return mItems;
    }
}

