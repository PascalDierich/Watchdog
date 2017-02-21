package de.pascaldierich.model.services.youtube;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
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

//    private String[] testNames = {
//        "SemperVideo", "Semper Video", "PascalDierich", "Pascal Dierich", "GoogleDevelopers", "Google Developers"
//    };

    private String[] testNames = {
            "Pascal Dierich"
    };

    @Test
    public void YOUTUBE_API_CHANNEL_RESPONSE_NOT_NULL() {
        for (String name: testNames) {
            TEST_WITH_NAME(name);
        }
    }

    private void TEST_WITH_NAME(String name) {
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

            System.out.println("NullPointer Check passed");

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }

        CHECK_FOR_ELEMENTS(youTubeChannelsPage);
    }

    private void CHECK_FOR_ELEMENTS(YouTubeChannelsPage page) {
        assertTrue(page.getKind().equalsIgnoreCase("youtube#channelListResponse"));
        assertTrue(!page.getEtag().isEmpty());
        assertTrue(page.getPageInfo() != null);
        System.out.println("Header Check passed");

        assertTrue("totalResults < 0", page.getPageInfo().getTotalResults() >= 0);
        assertTrue("ResultsPerPage != 5", page.getPageInfo().getResultsPerPage() == 5);
        System.out.println("PageInfo Check passed");

        assertTrue(page.getItems() != null);
        System.out.println("Item Check passed");
        System.out.println("Item.size == " + page.getItems().size());

    }
}
