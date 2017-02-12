package de.pascaldierich.model.network.models.plus.activities.item.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItemObject {
    @SerializedName("objectType")
    @Expose
    private String objectType;
    @SerializedName("actor")
    @Expose
    private Actor_ actor;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("replies")
    @Expose
    private Replies replies;
    @SerializedName("plusoners")
    @Expose
    private Plusoners plusoners;
    @SerializedName("resharers")
    @Expose
    private Resharers resharers;
    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Actor_ getActor() {
        return actor;
    }

    public void setActor(Actor_ actor) {
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

    public Replies getReplies() {
        return replies;
    }

    public void setReplies(Replies replies) {
        this.replies = replies;
    }

    public Plusoners getPlusoners() {
        return plusoners;
    }

    public void setPlusoners(Plusoners plusoners) {
        this.plusoners = plusoners;
    }

    public Resharers getResharers() {
        return resharers;
    }

    public void setResharers(Resharers resharers) {
        this.resharers = resharers;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    LOG_TAG = PlusActivitiesItemObject.class.getSimpleName();
}
