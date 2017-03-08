package de.pascaldierich.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.model.local.WatchdogContract;
import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.GoogleClient;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;
import de.pascaldierich.model.network.services.YouTubeService;
import hugo.weaving.DebugLog;

/**
 * Api for 'app'.
 * Model is the only instantiated class inside 'app'
 * and works as Boundary between them.
 * <p/>
 * Model always assumes the transmitted data is NonNull and got tested.
 * <b>Every Layer is responsible for transmitting data correctly.</b>
 * <p/>
 * Model converts data for 'app' in <b>3</b> possible POJO's defined in package domainmodels.
 * Observable -> represents an Observable ¯\_(ツ)_/¯
 * Post -> represents one Post, Tweet, Video (depends on Network)
 * Site -> represents one entry from 'Sites'
 *
 * @version 1.0
 */
public class Model {
    
    /********************************************************************************************
     * Instantiation:
     * <p>
     * Singleton with 'initialization-on-demand holder idiom'.
     ********************************************************************************************/
    
    // Converter for app - model models
    private Converter mConverter;
    
    private Model() {
        mConverter = new Converter();
    }
    
    public static Model getInstance() {
        return ModelHolder.INSTANCE;
    }
    
    private static class ModelHolder {
        private static final Model INSTANCE = new Model();
    }
    
    /********************************************************************************************
     * Network Methods:
     *
     * Model consists of 2 kind of network methods.
     * <b>search</b> and <b>getId</b>.
     * On both methods you have to append the intern network name (@interface SupportedNetworks).
     *
     * search:
     *      returns ApiResponse for latest News as POJO
     *
     * getId:
     *      returns ApiResponse as POJO with one or more possibly Id's
     ********************************************************************************************/

    /*
        YouTube
     */
    
    /**
     * Call the YouTube Search Service.
     * <p>
     *
     * @param key,          String: userKey defined in 'Sites' table
     * @param observableId, int: intern Id defined in 'Observables' table
     * @param time,         String as RFC3339: publishedAfter Parameter in Api-Request
     * @param range,        int: number of maxResults in Api-Response
     * @return POJO Collection, ArrayList<Post>
     * @throws ModelException
     */
    @DebugLog
    public ArrayList<Post> searchYouTube(@NonNull String key,
                                         int observableId,
                                         @NonNull String time,
                                         @IntRange(from = 1, to = 50) int range) throws ModelException {
        
        try {
            YouTubeSearchPage page = GoogleClient.getService(YouTubeService.class)
                    .getVideos(
                            ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_SEARCH_PART,
                            key,
                            time,
                            ConstantsApi.YOUTUBE_SEARCH_EVENT_TYPE,
                            range,
                            ConstantsApi.YOUTUBE_SEARCH_ORDER,
                            ConstantsApi.YOUTUBE_SEARCH_TYPE
                    ).execute().body();
            
            return mConverter.getPost(page, observableId);
        } catch (IOException e) {
            throw new ModelException(ModelErrorsCodes.Network.IO_ERROR);
        }
    }
    
    /**
     * Get possible YouTube-intern-Id's for given name
     * <p>
     *
     * @param name,  String: Name of the requested Observable
     * @param range, int: number of maxResults in Api-Response
     * @return POJO Collection, ArrayList<Site>
     * @throws ModelException
     */
    @DebugLog
    public ArrayList<Site> getIdYouTube(@NonNull String name,
                                        @IntRange(from = 1, to = 50) int range) throws ModelException {
        
        try {
            YouTubeChannelsPage page = GoogleClient.getService(YouTubeService.class)
                    .getChannelId(
                            ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_CHANNEL_PART,
                            name,
                            range
                    ).execute().body();
            
            return mConverter.getSite(page);
        } catch (IOException e) {
            throw new ModelException(ModelErrorsCodes.Network.IO_ERROR);
        }
    }
    
    
    /********************************************************************************************
     * Storage Methods:
     *
     * Model consists of 3 kind of storage methods.
     * <b>get</b>, <b>set</b> and <b>remove</b>.
     * On all methods you have to append the domain-model name (@package model.domainmodels).
     *
     * <b>Note:</b> all <b>get</b> storage-methods have to get called from Main-Thread!
     ********************************************************************************************/

    /*
        get Methods (read)
     */
    
    /**
     * Returns all Observables saved in DB
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Observable>
     * @throws ModelException
     */
    @MainThread
    public ArrayList<Observable> getObservables(Context context) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        Log.w("Model.class", "going to SetUp Loader");
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Observables.COLUMN_USER_ID,
                WatchdogContract.Observables.COLUMN_NAME,
                WatchdogContract.Observables.COLUMN_THUMBNAIL});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Observables.COLUMN_USER_ID);
        try {
            Log.w("Model.class", "going to load data and call Converter.class");
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getObservable(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns ObservableId for given Uri
     * <p/>
     *
     * @param context, Context: to access DB
     * @return id, long: unique id
     * @throws ModelException
     */
    @DebugLog
    @Deprecated
    @MainThread
    public long getObservables(Context context, Uri uri) throws ModelException {
        Log.w("Model.class", "getObservables: uri = " + uri);
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(uri);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Observables.COLUMN_USER_ID});
        try {
            Log.w("Model.class", "getObservables: going to call Converter");
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getObservableId(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Sites saved in DB
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Site>
     * @throws ModelException
     */
    @MainThread
    public ArrayList<Site> getSites(Context context) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Sites.CONTENT_URI_SITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Sites.COLUMN_USER_ID,
                WatchdogContract.Sites.COLUMN_SITE,
                WatchdogContract.Sites.COLUMN_KEY});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Sites.COLUMN_USER_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getSite(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Sites to specific Observable found by observableId
     * <p>
     *
     * @param context,      Context: to access DB
     * @param observableId, int: unique Id defined in table 'Observables'
     * @return POJO Collection, ArrayList<Site>: each Site for given Observable
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Site> getSites(Context context, int observableId) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Sites.CONTENT_URI_SITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Sites.COLUMN_USER_ID,
                WatchdogContract.Sites.COLUMN_SITE,
                WatchdogContract.Sites.COLUMN_KEY});
        // set selection to column 'userId'
        loaderWeakReference.get().setSelection("userId = ?"); // TODO: 22.02.17 don't know if works
        // set selectionArgs to parameter 'observableId'
        loaderWeakReference.get().setSelectionArgs(new String[] {Integer.toString(observableId)});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Sites.COLUMN_USER_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getSite(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Sites to specific name of Network (@interface SupportedNetworks)
     * <p>
     *
     * @param context, Context: to access DB
     * @param site,    String: name of supported Network
     * @return POJO Collection, ArrayList<Site>: each Site for given Network
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Site> getSites(Context context, @SupportedNetworks String site) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Sites.CONTENT_URI_SITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Sites.COLUMN_USER_ID,
                WatchdogContract.Sites.COLUMN_SITE,
                WatchdogContract.Sites.COLUMN_KEY});
        // set selection to column 'site'
        loaderWeakReference.get().setSelection("site = ?"); // TODO: 22.02.17 don't know if works
        // set selectionArgs to parameter 'observableId'
        loaderWeakReference.get().setSelectionArgs(new String[] {site});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Sites.COLUMN_USER_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getSite(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Posts in 'Favorites'
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Post>: each Post inside 'Favorites'
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Post> getFavorites(Context context) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Posts.Favorites.CONTENT_URI_FAVORITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Posts.COLUMN_ID,
                WatchdogContract.Posts.COLUMN_USER_ID,
                WatchdogContract.Posts.COLUMN_THUMBNAIL_URL,
                WatchdogContract.Posts.COLUMN_DESCRIPTION,
                WatchdogContract.Posts.COLUMN_TITLE,
                WatchdogContract.Posts.COLUMN_POST_ID,
                WatchdogContract.Posts.COLUMN_SITE,
                WatchdogContract.Posts.Favorites.COLUMN_TIME_SAVED});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Posts.COLUMN_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getPost(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Posts in 'Favorites' for given observableId
     * <p>
     *
     * @param context,      Context: to access DB
     * @param observableId, int: unique Id defined in table 'Observables'
     * @return POJO Collection, ArrayList<Post>: each Post inside 'Favorites' for given observableId
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Post> getFavorites(Context context, int observableId) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Posts.Favorites.CONTENT_URI_FAVORITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Posts.COLUMN_ID,
                WatchdogContract.Posts.COLUMN_USER_ID,
                WatchdogContract.Posts.COLUMN_THUMBNAIL_URL,
                WatchdogContract.Posts.COLUMN_DESCRIPTION,
                WatchdogContract.Posts.COLUMN_TITLE,
                WatchdogContract.Posts.COLUMN_POST_ID,
                WatchdogContract.Posts.COLUMN_SITE,
                WatchdogContract.Posts.Favorites.COLUMN_TIME_SAVED});
        // set selection to column 'userId'
        loaderWeakReference.get().setSelection("userId = ?"); // TODO: 22.02.17 don't know if works
        // set selectionArgs to parameter 'observableId'
        loaderWeakReference.get().setSelectionArgs(new String[] {Integer.toString(observableId)});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Posts.COLUMN_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getPost(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Posts in 'NewsFeed'
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Post>: each Post inside 'NewsFeed'
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Post> getNewsFeed(Context context) throws ModelException {
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Posts.NewsFeed.CONTENT_URI_NEWS_FEED);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Posts.COLUMN_ID,
                WatchdogContract.Posts.COLUMN_USER_ID,
                WatchdogContract.Posts.COLUMN_THUMBNAIL_URL,
                WatchdogContract.Posts.COLUMN_DESCRIPTION,
                WatchdogContract.Posts.COLUMN_TITLE,
                WatchdogContract.Posts.COLUMN_POST_ID,
                WatchdogContract.Posts.COLUMN_SITE,
                WatchdogContract.Posts.NewsFeed.COLUMN_TIME_DOWNLOADED});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Posts.COLUMN_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            return mConverter.getPost(entries);
        } catch (UnsupportedOperationException e) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Returns all Posts in 'NewsFeed' for given observableId
     * <p>
     *
     * @param context,      Context: to access DB
     * @param observableId, int: unique Id defined in table 'Observables'
     * @return POJO Collection, ArrayList<Post>: each Post inside 'NewsFeed' for given observableId
     * @throws ModelException
     */
    @DebugLog
    @MainThread
    public ArrayList<Post> getNewsFeed(Context context, int observableId) throws ModelException {
        Log.w("Model.class", "getNewsFeed starts...");
        // Instantiation
        Looper.prepare();
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(new CursorLoader(context));
        
        Log.w("Model.class", "going to setup Loader");
        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Posts.NewsFeed.CONTENT_URI_NEWS_FEED);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Posts.COLUMN_ID,
                WatchdogContract.Posts.COLUMN_USER_ID,
                WatchdogContract.Posts.COLUMN_THUMBNAIL_URL,
                WatchdogContract.Posts.COLUMN_DESCRIPTION,
                WatchdogContract.Posts.COLUMN_TITLE,
                WatchdogContract.Posts.COLUMN_POST_ID,
                WatchdogContract.Posts.COLUMN_SITE,
                WatchdogContract.Posts.NewsFeed.COLUMN_TIME_DOWNLOADED});
        // set selection to column 'userId'
        loaderWeakReference.get().setSelection("userId = ?"); // TODO: 22.02.17 don't know if works
        // set selectionArgs to parameter 'observableId'
        loaderWeakReference.get().setSelectionArgs(new String[] {Integer.toString(observableId)});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Posts.COLUMN_ID);
        
        try {
            Cursor entries = loaderWeakReference.get().loadInBackground();
            Log.w("Model.class", "Cursor received, going to call Converter");
            return mConverter.getPost(entries);
        } catch (UnsupportedOperationException e) {
            Log.w("Model.class", "UnknownUri Exception");
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }


    /*
        set Methods (write)
     */
    
    /**
     * Write a new Observable in table 'Observables'
     * <p>
     *
     * @param context,     Context: to access ContentResolver
     * @param observables, Observable, POJO to write in 'Observables'
     * @return id, long: unique ObservableId
     * @throws ModelException
     */
    public long setObservable(Context context, Observable observables) throws ModelException {
        try {
            Uri uri = context.getContentResolver()
                    .insert(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES,
                            mConverter.getContentValues(observables));
            
            return Long.parseLong(uri.getLastPathSegment());
//            return this.getObservables(context, uri);
        } catch (SQLException e) {
            throw new ModelException(ModelErrorsCodes.Storage.INSERT_FAILED);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Write a new Site in table 'Sites'
     * <p>
     *
     * @param context, Context: to access ContentResolver
     * @param site,    Site, POJO to write in 'Sites'
     * @return id, long: unique PostId
     * @throws ModelException
     */
    public long setSite(Context context, Site site) throws ModelException {
        try {
            Uri uri = context.getContentResolver()
                    .insert(WatchdogContract.Sites.CONTENT_URI_SITES,
                            mConverter.getContentValues(site));
    
            return Long.parseLong(uri.getLastPathSegment());
        } catch (SQLException e) {
            throw new ModelException(ModelErrorsCodes.Storage.INSERT_FAILED);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Write a new Post in table 'Favorites'
     * <p>
     *
     * @param context, Context: to access ContentResolver
     * @param post,    Post, POJO to write in 'Favorites'
     * @return id, long: unique PostId
     * @throws ModelException
     */
    public long setFavorite(Context context, Post post) throws ModelException {
        try {
            Uri uri = context.getContentResolver()
                    .insert(WatchdogContract.Posts.Favorites.CONTENT_URI_FAVORITES,
                            mConverter.getContentValues(post));
    
            return Long.parseLong(uri.getLastPathSegment());
        } catch (SQLException e) {
            throw new ModelException(ModelErrorsCodes.Storage.INSERT_FAILED);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Write a new Post in table 'NewsFeed'
     * <p>
     *
     * @param context, Context: to access ContentResolver
     * @param post,    Post, POJO to write in 'NewsFeed'
     * @return id, long: unique PostId
     * @throws ModelException
     */
    public long setNewsFeed(Context context, Post post) throws ModelException {
        try {
            Uri uri = context.getContentResolver()
                    .insert(WatchdogContract.Posts.NewsFeed.CONTENT_URI_NEWS_FEED,
                            mConverter.getContentValues(post));
            
            return Long.parseLong(uri.getLastPathSegment());
        } catch (SQLException e) {
            throw new ModelException(ModelErrorsCodes.Storage.INSERT_FAILED);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * Write a new Post Collection in table 'NewsFeed'
     * <p>
     *
     * @param context, Context: to access ContentResolver
     * @param postList,    ArrayLIst<Post>, POJO's to write in 'NewsFeed'
     * @return counts, int: number of rows added
     * @throws ModelException
     */
    public long setNewsFeed(Context context, ArrayList<Post> postList) throws ModelException {
        try {
            return context.getContentResolver()
                    .bulkInsert(WatchdogContract.Posts.NewsFeed.CONTENT_URI_NEWS_FEED,
                            mConverter.getContentValues(postList));
        } catch (SQLException e) {
            throw new ModelException(ModelErrorsCodes.Storage.INSERT_FAILED);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }


    /*
        remove Methods (delete)
     */
    
    /**
     * delete all Observables
     * <p>
     *
     * @param context, Context: to access ContentResolver
     */
    @DebugLog
    public void removeObservable(Context context) throws ModelException {
        try {
            context.getContentResolver()
                    .delete(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES,
                            null, null);
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
    /**
     * delete specific Observables
     * <p>
     *
     * @param context,      Context: to access ContentResolver
     * @param observableId, int: unique Id defined in table 'Observables'
     */
    @DebugLog
    public void removeObservable(Context context, int observableId) throws ModelException {
        try {
            context.getContentResolver()
                    .delete(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES,
                            "userId = ?", new String[] {Integer.toString(observableId)}); // TODO: 04.03.17 don't know if works 
        } catch (UnsupportedOperationException ue) {
            throw new ModelException(ModelErrorsCodes.Storage.UNKNOWN_URI);
        }
    }
    
}
