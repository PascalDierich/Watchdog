package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.GoogleClient;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;
import de.pascaldierich.model.network.services.YouTubeService;

/**
 * Singleton Repository Class for interacting with 'model' module
 * -> lazy instantiation (!not Thread save!)
 *
 * @version 1.0
 */
public class Model {

    /*
        Instantiation
     */

    // Converter for app - model models
    Converter mConverter;

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


    /*
        Network Methods
     */

    /**
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
     * @param id,    String: userId defined in 'Sites' table
     * @param time,  String as RFC3339: publishedAfter Parameter in Api-Request
     * @param range, int: number of maxResults in Api-Response
     * @return POJO Collection, ArrayList<Post>
     * @throws ModelException
     */
    public ArrayList<Post> searchYouTube(@NonNull String id,
                                         @NonNull String time,
                                         @IntRange(from = 1, to = 50) int range) throws ModelException {

        try {
            YouTubeSearchPage page = GoogleClient.getService(YouTubeService.class)
                    .getVideos(
                            ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_SEARCH_PART,
                            id,
                            time,
                            ConstantsApi.YOUTUBE_SEARCH_EVENT_TYPE,
                            range,
                            ConstantsApi.YOUTUBE_SEARCH_ORDER,
                            ConstantsApi.YOUTUBE_SEARCH_TYPE
                    ).execute().body();

            return mConverter.getPost(page, id);
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


    /*
        Storage Methods
     */


}
