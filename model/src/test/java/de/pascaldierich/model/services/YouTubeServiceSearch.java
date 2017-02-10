package de.pascaldierich.model.services;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.YouTubeSearchPage;
import de.pascaldierich.model.network.services.YouTubeService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeServiceSearch {

    private String time = "2017-01-01T00:00:00Z"; // RFC3339
    private String[] ids = {
            "UC_x5XG1OV2P6uZZ5FSM9Ttw",
            "UCCI6C8hD-hTZi2JEmS7zvQw"
    };

    @Test
    public void YOUTUBE_API_SEARCH_RESPONSE_NOT_NULL() {
        for (String id: ids) {
            TEST_WITH_ID(id, time);
        }
    }

    private void TEST_WITH_ID(String id, String time) {
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build();

        Retrofit mGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeService youTubeService = mGoogleClient.create(YouTubeService.class);
        YouTubeSearchPage youTubeSearchPage = null;
        try {
            youTubeSearchPage =
                    youTubeService.getVideos(ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_SEARCH_PART,
                            id,
                            time,
                            ConstantsApi.YOUTUBE_SEARCH_EVENT_TYPE,
                            5,
                            ConstantsApi.YOUTUBE_SEARCH_ORDER,
                            ConstantsApi.YOUTUBE_SEARCH_TYPE)
                            .execute().body();
            System.out.println("NullPointer Check passed");
        } catch (IOException e) {
            assertTrue("IOException", false);
        }
        CHECK_FOR_ELEMENTS(youTubeSearchPage);
    }

    private void CHECK_FOR_ELEMENTS(YouTubeSearchPage youTubeSearchPage) {

    }

}
