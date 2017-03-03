package de.pascaldierich.model.network.models.plus.activities.item.provider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItemProvider {
    @SerializedName("title")
    @Expose
    private String title;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
