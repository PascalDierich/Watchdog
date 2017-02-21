package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.IOException;

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
    /*
        Instantiation
     */
    private static Model sInstance = null;

    private Model() {

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
     * Model consists of 2 kinds of network methods.
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
     * @param id, String: userId defined as key in 'Sites' table
     * @param time, String as RFC3339: publishedAfter Parameter in Api-Request
     * @param range, int: number of maxResults in Api-Response
     * @return POJO, YouTubeSearchPage
     * @throws IOException
     */
    public YouTubeSearchPage searchYouTube(@NonNull String id,
                                           @NonNull String time,
                                           @IntRange(from = 1, to = 50) int range) throws IOException {

        // TODO: 21.02.17 change return POJO searchYouTube.
        // 1. Problem Dependency goes inside domain-layer
        // 2. POJO is too big and most information is not usable.
        //   --> define model class inside domain-layer
        //      --> return model class from domain-layer

        return GoogleClient.getService(YouTubeService.class)
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
    }

    /**
     * Get possibly YouTube-intern-Id's for given name
     * <p>
     * @param name, String: Name of the requested Observable
     * @param range, int: number of maxResults in Api-Response
     * @return POJO, YouTubeChannelsPage
     * @throws IOException
     */
    public YouTubeChannelsPage getIdYouTube(@NonNull String name,
                                            @IntRange(from = 1, to = 50) int range) throws IOException {

        // TODO: 21.02.17 change return POJO getIdYouTube.
        // 1. Problem Dependency goes inside domain-layer
        // 2. POJO is too big and most information is not usable.
        //   --> define model class inside domain-layer
        //      --> return model class from domain-layer

        return GoogleClient.getService(YouTubeService.class)
                .getChannelId(
                        ConstantsApi.YOUTUBE_API_KEY,
                        ConstantsApi.YOUTUBE_CHANNEL_PART,
                        name,
                        range
                ).execute().body();
    }

}
