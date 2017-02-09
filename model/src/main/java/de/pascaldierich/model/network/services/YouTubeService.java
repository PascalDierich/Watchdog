package de.pascaldierich.model.network.services;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import de.pascaldierich.model.network.ConstantsApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the YouTube Api
 */
public interface YouTubeService {

    /*
     * get latest published Video
     *      GET https://www.googleapis.com/youtube/v3/search
     */
    @GET("youtube/v3/search")
    Call<Object> getVideos(@Query("part") @ConstantsApi String part,
                           @Query("channelId") @NonNull String channelId,
                           @Query("publishedAfter") @NonNull String time,
                           @Query("eventType") @ConstantsApi String type,
                           @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);


    /*
     * get channelId
     *      GET https://www.googleapis.com/youtube/v3/channels
     */
    @GET("youtube/v3/channels")
    Call<Object> getChannelId(@Query("part") @ConstantsApi String part,
                              @Query("forUsername") @NonNull String name,
                              @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);
}

