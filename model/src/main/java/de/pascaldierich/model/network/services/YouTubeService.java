package de.pascaldierich.model.network.services;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.pages.YouTubeSearchPage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Retrofit Interface for the YouTube Api
 */
public interface YouTubeService {
    /*
     * get latest published Video
     *      GET https://www.googleapis.com/youtube/v3/search
     */
    @GET("youtube/v3/search")
    Call<YouTubeSearchPage> getVideos(@Query("key") @ConstantsApi String key,
                                      @Query("part") @ConstantsApi String part,
                                      @Query("channelId") @NonNull String channelId,
                                      @Query("publishedAfter") @NonNull String time,
                                      @Query("eventType") @ConstantsApi String type,
                                      @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);


    /*
     * get channelId
     *      GET https://www.googleapis.com/youtube/v3/channels
     */
    @GET("youtube/v3/channels")
    Call<YouTubeChannelsPage> getChannelId(@Query("key") @ConstantsApi String key,
                                           @Query("part") @ConstantsApi String part,
                                           @Query("forUsername") @NonNull String name,
                                           @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);
}

