package de.pascaldierich.model.services;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.YouTubeChannelsPage;
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

    private String[] testNames = {
        "SemperVideo", "Semper Video", "PascalDierich", "Pascal Dierich", "GoogleDevelopers", "Google Developers"
    };

    @Test
    public void YOUTUBE_API_CHANNEL_RESPONSE_NOT_NULL() {
        for (String name: testNames) {
            TEST_WITH_NAME(name);
        }
    }

    public void TEST_WITH_NAME(String name) {
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build();

        Retrofit mGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YouTubeService youTubeService = mGoogleClient.create(YouTubeService.class);
        YouTubeChannelsPage youTubeChannelsPage = null;
        try {
            youTubeChannelsPage =
                    youTubeService.getChannelId(ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_CHANNEL_PART,
                            name,
                            5)
                        .execute()
                        .body();

            assertTrue(youTubeChannelsPage != null);

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }
        assertTrue(youTubeChannelsPage.getEtag() != null);
        assertTrue(youTubeChannelsPage.getPageInfo() != null);
        assertTrue(youTubeChannelsPage.getItems() != null);

        assertTrue(youTubeChannelsPage.getKind().equalsIgnoreCase("youtube#channelListResponse"));

        assertTrue(youTubeChannelsPage.getPageInfo().getTotalResults() >= 1);

    }
}
