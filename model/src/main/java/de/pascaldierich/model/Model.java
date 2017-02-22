package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

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

/**
 * Singleton Repository Class for interacting with 'model' module
 *      -> lazy instantiation (!not Thread save!)
 *
 * @version 1.0
 */
public class Model {

    /******************
        Instantiation
     ******************/

    // Converter for app - model models
    private Converter mConverter;

    private static Model sInstance = null;

    private Model() {
        mConverter = new Converter();
    }

    public static Model getInstance() {
        if (sInstance == null) {
            sInstance = new Model();
        }
        return sInstance;
    }

    public static void finished() {
        sInstance = null;
    }

    /**
     * Network Methods:
     *
     * Model consists of 2 kind of network methods.
     * <b>search</b> and <b>getId</b>.
     * on both methods you have to append the intern network name (@interface SupportedNetworks).
     *
     * search:
     *      returns ApiResponse for latest News as POJO
     *
     * getId:
     *      returns ApiResponse as POJO with one or more possibly Id's
     */

    /**
     * Call the YouTube Search Service.
     * <p>
     *
     * @param key, String: userKey defined in 'Sites' table
     * @param observableId, int: intern Id defined in 'Observables' table
     * @param time,  String as RFC3339: publishedAfter Parameter in Api-Request
     * @param range, int: number of maxResults in Api-Response
     * @return POJO Collection, ArrayList<Post>
     * @throws ModelException
     */
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
            throw new ModelException(e.getMessage());
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
            throw new ModelException(e.getMessage());
        }
    }


    /**
     * Storage Methods:
     *
     */

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
    public ArrayList<Observable> getObservables(Context context) throws ModelException {
        // Instantiation
        CursorLoader mLoader = new CursorLoader(context);
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(mLoader);

        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Observables.COLUMN_USER_ID,
                WatchdogContract.Observables.COLUMN_NAME,
                WatchdogContract.Observables.COLUMN_THUMBNAIL});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Observables.COLUMN_USER_ID);

        Cursor entries = loaderWeakReference.get().loadInBackground();

        return mConverter.getObservable(entries);
    }

    /**
     * Returns all Sites saved in DB
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Site>
     * @throws ModelException
     */
    public ArrayList<Site> getSites(Context context) throws ModelException {
        // Instantiation
        CursorLoader mLoader = new CursorLoader(context);
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(mLoader);

        // Setup CursorLoader
        loaderWeakReference.get().setUri(WatchdogContract.Sites.CONTENT_URI_SITES);
        loaderWeakReference.get().setProjection(new String[] {
                WatchdogContract.Sites.COLUMN_USER_ID,
                WatchdogContract.Sites.COLUMN_SITE,
                WatchdogContract.Sites.COLUMN_KEY});
        loaderWeakReference.get().setSortOrder(WatchdogContract.Sites.COLUMN_USER_ID);

        Cursor entries = loaderWeakReference.get().loadInBackground();

        return mConverter.getSite(entries);
    }

    /**
     * Returns all Posts in 'Favorites'
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Post>
     * @throws ModelException
     */
    public ArrayList<Post> getFavorites(Context context) throws ModelException {
        // Instantiation
        CursorLoader mLoader = new CursorLoader(context);
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(mLoader);

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

        Cursor entries = loaderWeakReference.get().loadInBackground();

        return mConverter.getPost(entries);
    }

    /**
     * Returns all Posts in 'NewsFeed'
     * <p>
     *
     * @param context, Context: to access DB
     * @return POJO Collection, ArrayList<Post>
     * @throws ModelException
     */
    public ArrayList<Post> getNewsFeed(Context context) throws ModelException {
        // Instantiation
        CursorLoader mLoader = new CursorLoader(context);
        WeakReference<CursorLoader> loaderWeakReference = new WeakReference<>(mLoader);

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

        Cursor entries = loaderWeakReference.get().loadInBackground();

        return mConverter.getPost(entries);
    }


    /*
        set Methods (write)
     */

    public void setObservable(Context context, Observable observables) throws ModelException {
        try {
            context.getContentResolver()
                    .insert(WatchdogContract.Observables.CONTENT_URI_OBSERVABLES,
                            mConverter.getContentValues(observables));
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        } catch (UnsupportedOperationException ex) {
            throw new ModelException("intern database error");
        }
    }

    public void setSite(Context context, Site site) throws ModelException {
        try {
            context.getContentResolver()
                    .insert(WatchdogContract.Sites.CONTENT_URI_SITES,
                            mConverter.getContentValues(site));
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        } catch (UnsupportedOperationException ex) {
            throw new ModelException("intern database error");
        }
    }

    public void setFavorite(Context context, Post post) throws ModelException {
        try {
            context.getContentResolver()
                    .insert(WatchdogContract.Posts.Favorites.CONTENT_URI_FAVORITES,
                            mConverter.getContentValues(post));
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        } catch (UnsupportedOperationException ex) {
            throw new ModelException("intern database error");
        }
    }
}
