package de.pascaldierich.model.services.youtube;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.items.YouTubeSearchItem;
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

    private String time = "2016-01-01T00:00:00Z"; // RFC3339
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

            assertTrue(youTubeSearchPage != null);
            System.out.println("NullPointer Check passed");
        } catch (IOException e) {
            assertTrue("IOException", false);
        }
        CHECK_FOR_ELEMENTS(youTubeSearchPage);
    }

    private void CHECK_FOR_ELEMENTS(YouTubeSearchPage youTubeSearchPage) {
        assertTrue(youTubeSearchPage.getKind().equalsIgnoreCase("youtube#searchListResponse"));
        assertTrue(youTubeSearchPage.getEtag() != null);
        assertTrue(youTubeSearchPage.getPageInfo() != null);
        System.out.println("Header Check passed");

        assertTrue("totalResults < 0", youTubeSearchPage.getPageInfo().getTotalResults() >= 0);
        assertTrue("ResultsPerPage != 5", youTubeSearchPage.getPageInfo().getResultsPerPage() == 5);
        System.out.println("PageInfo Check passed");

        assertTrue(youTubeSearchPage.getItems() != null);
    
        if (youTubeSearchPage.getItems().size() > 0) {
            for (YouTubeSearchItem item: youTubeSearchPage.getItems()) {
                assertTrue(item != null);
                assertTrue(item.getKind().equalsIgnoreCase("youtube#searchResult"));
                assertTrue(item.getEtag() != null);
                assertTrue(item.getId() != null);
                assertTrue(item.getSnippet() != null);
                System.out.println("Item Header Check passed");

                assertTrue(item.getId().getKind().equalsIgnoreCase("youtube#video"));
                assertTrue(item.getId().getVideoId() != null);
                System.out.println("Id Check passed");

                assertTrue(item.getSnippet().getChannelId().equals("UC_x5XG1OV2P6uZZ5FSM9Ttw")
                        || item.getSnippet().getChannelId().equals("UCCI6C8hD-hTZi2JEmS7zvQw"));
                assertTrue(item.getSnippet().getTitle() != null);
                assertTrue(item.getSnippet().getDescription() != null);
                assertTrue(item.getSnippet().getChannelTitle() != null);
                assertTrue(item.getSnippet().getThumbnails() != null);
                System.out.println("Snippet Check passed");

                assertTrue(item.getSnippet().getThumbnails().getHigh() != null);
                System.out.println("Thumbnail Check passed");

                assertTrue(item.getSnippet().getThumbnails().getHigh().getHeight() > 0);
                assertTrue(item.getSnippet().getThumbnails().getHigh().getWidth() > 0);
                assertTrue(item.getSnippet().getThumbnails().getHigh().getUrl().contains(".jpg"));
                System.out.println("Thumbnail High Check passed");
            }
        }

    }

}
