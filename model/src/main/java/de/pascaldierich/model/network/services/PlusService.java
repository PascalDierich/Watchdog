package de.pascaldierich.model.network.services;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.PlusActivitiesPage;
import de.pascaldierich.model.network.models.pages.PlusPeoplePage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Retrofit Interface for the Google+ Api
 */
public interface PlusService {
    /*
     * get list of users
     *      GET https://www.googleapis.com/plus/v1/people
     */
    @GET("plus/v1/people")
    Call<PlusPeoplePage> getPeople(@Query("query") @NonNull String name,
                                   @Query("maxResults") @IntRange(from = 1, to = 50) int maxResults);
    
    /*
     * get list of Activities
     *      GET https://www.googleapis.com/plus/v1/people/userId/activities
     */
    @GET("plus/v1/people/{userId}/activities")
    Call<PlusActivitiesPage> getActivities(@Path("userId") @NonNull String userId,
                                           @Query("collection") @ConstantsApi String collection);
}
