package de.pascaldierich.model.network.services;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import de.pascaldierich.model.network.ConstantsApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the Google+ Api
 */
public interface PlusService {
    // TODO: 09.02.17 change return Object

    /*
     * get list of users
     *      GET https://www.googleapis.com/plus/v1/people
     */
    @GET("plus/v1/people")
    Call<Object> getPeople(@Query("query") @NonNull String name,
                           @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);
    
    /*
     * get list of Activities
     *      GET https://www.googleapis.com/plus/v1/people/userId/activities
     */
    @GET("plus/v1/people/{userId}/activities")
    Call<Object> getActivities(@Path("userId") @NonNull String userId,
                               @Query("collection") @ConstantsApi String collection);
}
