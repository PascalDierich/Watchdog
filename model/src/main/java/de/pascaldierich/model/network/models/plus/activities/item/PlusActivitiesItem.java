package de.pascaldierich.model.network.models.plus.activities.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.pascaldierich.model.network.models.plus.activities.item.access.PlusActivitiesItemAccess;
import de.pascaldierich.model.network.models.plus.activities.item.actor.PlusActivitiesItemActor;
import de.pascaldierich.model.network.models.plus.activities.item.object.PlusActivitiesItemObject;
import de.pascaldierich.model.network.models.plus.activities.item.provider.PlusActivitiesItemProvider;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusActivitiesItem {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("actor")
    @Expose
    private PlusActivitiesItemActor actor;
    @SerializedName("verb")
    @Expose
    private String verb;
    @SerializedName("object")
    @Expose
    private PlusActivitiesItemObject object;
    @SerializedName("provider")
    @Expose
    private PlusActivitiesItemProvider provider;
    @SerializedName("access")
    @Expose
    private PlusActivitiesItemAccess access;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PlusActivitiesItemActor getActor() {
        return actor;
    }

    public void setActor(PlusActivitiesItemActor actor) {
        this.actor = actor;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public PlusActivitiesItemObject getObject() {
        return object;
    }

    public void setObject(PlusActivitiesItemObject object) {
        this.object = object;
    }

    public PlusActivitiesItemProvider getProvider() {
        return provider;
    }

    public void setProvider(PlusActivitiesItemProvider provider) {
        this.provider = provider;
    }

    public PlusActivitiesItemAccess getAccess() {
        return access;
    }

    public void setAccess(PlusActivitiesItemAccess access) {
        this.access = access;
    }
}