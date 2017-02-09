package de.pascaldierich.model.network;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofits Client for Google Services
 *      - YouTube
 *      - Google+
 */
public class GoogleClient {

    private static Retrofit sGoogleClient;

    static {
        sGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return sGoogleClient.create(serviceClass);
    }
}
