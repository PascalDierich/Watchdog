package de.pascaldierich.model.services;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.GoogleClient;
import de.pascaldierich.model.network.models.pages.YouTubeChannelsPage;
import de.pascaldierich.model.network.services.YouTubeService;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeServiceChannel {
    private static final String LOG_TAG = YouTubeServiceChannel.class.getSimpleName();

    @Test
    public void YouTube_Api_Channel_Response_Not_Null() {
        YouTubeService youTubeService = GoogleClient.getService(YouTubeService.class);

        assertTrue(youTubeService != null);

        YouTubeChannelsPage youTubeChannelsPages;
        try {
            youTubeChannelsPages = youTubeService
                    .getChannelId(ConstantsApi.YOUTUBE_CHANNEL_PART, "Semper Video", 5)
                    .execute()
                    .body();

            assertTrue(youTubeChannelsPages != null);
            Log.d(LOG_TAG, "YouTube_Api_Response_Not_Null: Response size = " + youTubeChannelsPages.getTotalResults());

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }
    }
}
