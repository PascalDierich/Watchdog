package de.pascaldierich.model.services;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.services.YouTubeService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeServiceChannel {
    private static final String LOG_TAG = YouTubeServiceChannel.class.getSimpleName();

    @Test
    public void YouTube_Api_Channel_Response_Not_Null() {
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build();

        Retrofit mGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeService youTubeService = mGoogleClient.create(YouTubeService.class);

        try {
            assertTrue(
                    youTubeService.getChannelId(ConstantsApi.YOUTUBE_CHANNEL_PART, "Semper Video", 5)
                        .execute()
                        .body() != null);

//            assertTrue(youTubeChannelsPages != null);
//            Log.d(LOG_TAG, "YouTube_Api_Response_Not_Null: Response size = " + youTubeChannelsPages.getPageInfo());

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }
    }
}
