package de.pascaldierich.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.model.local.WatchdogContract;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;
import hugo.weaving.DebugLog;

/**
 * app - model Converter.
 * Get's instantiated by Model.class and converts POJO's for app - model communication.
 */
public class Converter {
    // This boolean value indicates if there are SQL-specific values (_ID and timestamp)
    private boolean gotDownloaded;
    
    // TODO: 10.03.17 use one ArrayList with Generics for all methods
    
    /********************************************************************************************
     * getPost methods
     *
     ********************************************************************************************/
    
    /**
     * Converts downloaded YouTubeSearch result to Post Collection
     * <p>
     * <b>gotDownloaded = false</b>
     *
     * @param page,   YouTubeSearchPage: API response
     * @param userId, int: intern Id for this Observable
     * @return result, ArrayList<Post>: Collection of all Posts
     * @throws ModelException
     */
    public ArrayList<Post> getPost(@Nullable YouTubeSearchPage page, int userId) throws ModelException {
        ArrayList<Post> result = new ArrayList<>();
        gotDownloaded = false;
        
        // Because Model.class only gets called after ConnectionTest,
        // this indicates that the channelId is invalid.
        if (page == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        // Because the JSON String got converted to POJO correctly (because not null),
        // this indicates that there were no found entries.
        //      --> No new Posts
        if (page.getItems().isEmpty())
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        try {
            for (int i = 0; i < page.getItems().size(); i++) {
                result.add(new Post()
                        .setGotDownloaded(gotDownloaded)
                        .setUserId(userId)
                        .setSite(SupportedNetworks.YOUTUBE)
                        .setThumbnailUrl(page.getItems().get(i).getSnippet().getThumbnails().getHigh().getUrl())
                        .setDescription(page.getItems().get(i).getSnippet().getDescription())
                        .setTitle(page.getItems().get(i).getSnippet().getTitle())
                        .setPostId(page.getItems().get(i).getId().getVideoId())
                );
            }
            return result;
        } catch (RuntimeException e) {
            throw new ModelException(ModelErrorsCodes.Converter.RUNTIME_ERROR);
        }
    }
    
    /**
     * Converts Cursor result to Post Collection
     * <p>
     * <b>gotDownloaded = true</b>
     *
     * @param entries, Cursor: Provider response
     * @return result, ArrayList<Post>: Collection of all given Posts in Cursor
     * @throws ModelException
     */
    public ArrayList<Post> getPost(@Nullable Cursor entries) throws ModelException {
        ArrayList<Post> result = new ArrayList<>();
        gotDownloaded = true;
        
        if (entries == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            entries.close();
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        }
        
        if (entries.getCount() == 0)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        do {
            result.add(new Post()
                    .setGotDownloaded(gotDownloaded)
                    .set_ID(entries.getInt(WatchdogContract.Posts.COLUMN_ID_ID))
                    .setUserId(entries.getInt(WatchdogContract.Posts.COLUMN_USER_ID_ID))
                    .setThumbnailUrl(entries.getString(WatchdogContract.Posts.COLUMN_THUMBNAIL_URL_ID))
                    .setDescription(entries.getString(WatchdogContract.Posts.COLUMN_DESCRIPTION_ID))
                    .setTitle(entries.getString(WatchdogContract.Posts.COLUMN_TITLE_ID))
                    .setPostId(entries.getString(WatchdogContract.Posts.COLUMN_POST_ID_ID))
                    .setSite(entries.getString(WatchdogContract.Posts.COLUMN_SITE_ID))
                    .setTimestamp(entries.getString(WatchdogContract.Posts.COLUMN_TIMESTAMP_ID))
            );
        } while (entries.moveToNext());
        entries.close();
        
        return result;
    }
    
    
    /********************************************************************************************
     * getSite methods
     *
     ********************************************************************************************/
    
    /**
     * Converts downloaded YouTubeChannel result to Site Collection
     * <p>
     * <b>gotDownloaded = false</b>
     *
     * @param page YouTubeSearchPage: API response
     * @return result, ArrayList<Site>: Collection of all Sites
     * @throws ModelException
     */
    public ArrayList<Site> getSite(@Nullable YouTubeChannelsPage page) throws ModelException {
        ArrayList<Site> result = new ArrayList<>();
        gotDownloaded = false;
        
        // Because Model.class only gets called after ConnectionTest,
        // this indicates that the channelId is invalid.
        if (page == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        // Because the JSON String got converted to POJO correctly (because not null),
        // this indicates that there were no found entries.
        //      --> No new Posts
        if (page.getItems().isEmpty())
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        try {
            for (int i = 0; i < page.getItems().size(); i++) {
                result.add(new Site()
                        .setGotDownloaded(gotDownloaded)
                        .setSite(SupportedNetworks.YOUTUBE)
                        .setKey(page.getItems().get(i).getId())
                );
            }
            return result;
        } catch (RuntimeException e) {
            throw new ModelException(ModelErrorsCodes.Converter.RUNTIME_ERROR);
        }
    }
    
    /**
     * Converts Cursor result to Site Collection
     * <p>
     * <b>gotDownloaded = true</b>
     *
     * @param entries, Cursor: Provider response
     * @return result, ArrayList<Site>: Collection of all given Sites in Cursor
     * @throws ModelException
     */
    @DebugLog
    public ArrayList<Site> getSite(@Nullable Cursor entries) throws ModelException {
        ArrayList<Site> result = new ArrayList<>();
        gotDownloaded = true;
        
        if (entries == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            entries.close();
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        }
        
        if (entries.getCount() == 0)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        do {
            result.add(new Site()
                    .setGotDownloaded(gotDownloaded)
                    .setUserId(entries.getInt(WatchdogContract.Sites.COLUMN_USER_ID_ID))
                    .setSite(entries.getString(WatchdogContract.Sites.COLUMN_SITE_ID))
                    .setKey(entries.getString(WatchdogContract.Sites.COLUMN_KEY_ID))
            );
        } while (entries.moveToNext());
        entries.close();
        
        return result;
    }
    
    
    /********************************************************************************************
     * getObservable methods
     *
     ********************************************************************************************/
    
    /**
     * Converts Cursor result to Observable Collection
     * <p>
     *
     * @param entries, Cursor: Provider response
     * @return result, ArrayList<Observable>: Collection of all given Observables in Cursor
     * @throws ModelException
     */
    public ArrayList<Observable> getObservable(@Nullable Cursor entries) throws ModelException {
        ArrayList<Observable> result = new ArrayList<>();
        Log.w("Converter.class", "received entries, going to check");
        if (entries == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            entries.close();
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        }
        
        if (entries.getCount() == 0)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        do {
            Observable item = new Observable();
            item.setUserId(entries.getInt(WatchdogContract.Observables.COLUMN_USER_ID_ID))
                    .setDisplayName(entries.getString(WatchdogContract.Observables.COLUMN_NAME_ID));
    
            if (entries.getBlob(WatchdogContract.Observables.COLUMN_THUMBNAIL_ID) == null) {
                item.setThumbnail(null);
                item.setGotThumbnail(false);
            } else {
                item.setThumbnail(entries.getBlob(WatchdogContract.Observables.COLUMN_THUMBNAIL_ID));
                item.setGotThumbnail(true);
            }
            
            result.add(item);
        } while (entries.moveToNext());
        entries.close();
        
        Log.w("Converter.class", "going to return converted Collection");
        return result;
    }
    
    
    /********************************************************************************************
     * getContentValues methods
     *
     * Parameters are annotated with 'NonNull' and the methods assumes
     * that the Object are already checked and can get converted directly.
     *      --> Because the Parameters come directly from the app
     *
     * The keys are named by the equivalent table-rows!
     ********************************************************************************************/
    
    /**
     * Converts observable model to ContentValues.
     * <p>
     *
     * @param observable, Observable
     * @return result, ContentValues: converted Observable with ColumnName's as key
     */
    @DebugLog
    public ContentValues getContentValues(@NonNull Observable observable) {
        ContentValues result = new ContentValues();
        
        // Thumbnail is optional
        if (observable.getGotThumbnail())
            result.put(WatchdogContract.Observables.COLUMN_THUMBNAIL, observable.getThumbnail());
        
        result.put(WatchdogContract.Observables.COLUMN_NAME, observable.getDisplayName());
        
        return result;
    }
    
    /**
     * Converts site model to ContentValues.
     * <p>
     *
     * @param site, Site
     * @return result, ContentValues: converted Site with ColumnName's as key
     */
    @DebugLog
    public ContentValues getContentValues(@NonNull Site site) {
        ContentValues result = new ContentValues();
        
        result.put(WatchdogContract.Sites.COLUMN_SITE, site.getSite());
        result.put(WatchdogContract.Sites.COLUMN_KEY, site.getKey());
        result.put(WatchdogContract.Sites.COLUMN_USER_ID, site.getUserId());
        
        return result;
    }
    
    /**
     * Converts post model to ContentValues.
     * This method is independent from the different Post-tables.
     * <p>
     *
     * @param post, Post
     * @return result, ContentValues: converted Post with ColumnName's as key
     */
    public ContentValues getContentValues(@NonNull Post post) {
        ContentValues result = new ContentValues();
        
        result.put(WatchdogContract.Posts.COLUMN_USER_ID, post.getUserId());
        result.put(WatchdogContract.Posts.COLUMN_THUMBNAIL_URL, post.getThumbnailUrl());
        result.put(WatchdogContract.Posts.COLUMN_DESCRIPTION, post.getDescription());
        result.put(WatchdogContract.Posts.COLUMN_TITLE, post.getTitle());
        result.put(WatchdogContract.Posts.COLUMN_POST_ID, post.getPostId());
        result.put(WatchdogContract.Posts.COLUMN_SITE, post.getSite());
        
        return result;
    }
    
    /**
     * Converts post model to ContentValues Array.
     * This method is independent from the different Post-tables.
     * <p>
     *
     * @param postList, ArrayList<Post>
     * @return result, ContentValues[]: converted Post with ColumnName's as key
     */
    public ContentValues[] getContentValues(@NonNull ArrayList<Post> postList) {
        ContentValues[] result = new ContentValues[postList.size()];
    
        for (int i = 0; i < postList.size(); i++) {
            result[i] = new ContentValues();
            result[i].put(WatchdogContract.Posts.COLUMN_USER_ID, postList.get(i).getUserId());
            result[i].put(WatchdogContract.Posts.COLUMN_THUMBNAIL_URL, postList.get(i).getThumbnailUrl());
            result[i].put(WatchdogContract.Posts.COLUMN_DESCRIPTION, postList.get(i).getDescription());
            result[i].put(WatchdogContract.Posts.COLUMN_TITLE, postList.get(i).getTitle());
            result[i].put(WatchdogContract.Posts.COLUMN_POST_ID, postList.get(i).getPostId());
            result[i].put(WatchdogContract.Posts.COLUMN_SITE, postList.get(i).getSite());
        }
        
        return result;
    }
    
    
    /********************************************************************************************
     * getId methods
     *
     ********************************************************************************************/
    
    @DebugLog
    public long getObservableId(Cursor entries) throws ModelException {
        if (entries == null)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_NULL);
        
        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            entries.close();
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        }
        
        if (entries.getCount() == 0)
            throw new ModelException(ModelErrorsCodes.Converter.PARAMETER_EMPTY);
        
        return entries.getInt(WatchdogContract.Observables.COLUMN_USER_ID_ID);
    }
}
