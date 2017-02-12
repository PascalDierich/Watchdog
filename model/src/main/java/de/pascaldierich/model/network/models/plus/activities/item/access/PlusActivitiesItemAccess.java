package de.pascaldierich.model.network.models.plus.activities.item.access;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItemAccess {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("items")
    @Expose
    private ArrayList<PlusActivitiesItemAccessItem> items = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<PlusActivitiesItemAccessItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PlusActivitiesItemAccessItem> items) {
        this.items = items;
    }
}
