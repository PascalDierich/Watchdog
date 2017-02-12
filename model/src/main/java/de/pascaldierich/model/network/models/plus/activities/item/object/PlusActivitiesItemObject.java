package de.pascaldierich.model.network.models.plus.activities.item.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.pascaldierich.model.network.models.plus.activities.item.actor.PlusActivitiesItemActor;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItemObject {
    @SerializedName("objectType")
    @Expose
    private String objectType;
    @SerializedName("actor")
    @Expose
    private PlusActivitiesItemActor actor;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;
//    @SerializedName("attachments")
//    @Expose
//    private List<Attachment> attachments = null;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public PlusActivitiesItemActor getActor() {
        return actor;
    }

    public void setActor(PlusActivitiesItemActor actor) {
        this.actor = actor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public List<Attachment> getAttachments() {
//        return attachments;
//    }
//
//    public void setAttachments(List<Attachment> attachments) {
//        this.attachments = attachments;
//    }
}
