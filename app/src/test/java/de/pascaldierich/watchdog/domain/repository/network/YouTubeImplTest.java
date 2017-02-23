package de.pascaldierich.watchdog.domain.repository.network;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.domain.repository.network.impl.YouTubeImpl;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchItem;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class YouTubeImplTest {
    private final int range = 10;
    private String time = "2016-01-01T00:00:00Z"; // RFC3339
    private String id = "UC_x5XG1OV2P6uZZ5FSM9Ttw";

    @Test
    public void YOUTUBE_IMPL_NOT_NULL() {
        YouTubeImpl youTube = new YouTubeImpl();
        CHANNEL_TEST(youTube);
        SEARCH_TEST(youTube);

    }

    private void SEARCH_TEST(YouTubeImpl youTube) {
        try {
            YouTubeSearchPage result = youTube.searchYouTube(id, time, range);

            assertTrue(result != null);
            System.out.println("NullPointer Check passed");

            CHECK_FOR_ELEMENTS_SEARCH(result);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private void CHECK_FOR_ELEMENTS_SEARCH(YouTubeSearchPage page) {
        Assert.assertTrue(page.getKind().equalsIgnoreCase("youtube#searchListResponse"));
        Assert.assertTrue(page.getEtag() != null);
        Assert.assertTrue(page.getPageInfo() != null);
        System.out.println("Header Check passed");

        Assert.assertTrue("totalResults < 0", page.getPageInfo().getTotalResults() >= 0);
        Assert.assertTrue("ResultsPerPage != " + range, page.getPageInfo().getResultsPerPage() == range);
        System.out.println("PageInfo Check passed");

        Assert.assertTrue(page.getItems() != null);

        if (page.getItems().size() > 0) {
            for (YouTubeSearchItem item : page.getItems()) {
                Assert.assertTrue(item != null);
                Assert.assertTrue(item.getKind().equalsIgnoreCase("youtube#searchResult"));
                Assert.assertTrue(item.getEtag() != null);
                Assert.assertTrue(item.getId() != null);
                Assert.assertTrue(item.getSnippet() != null);
                System.out.println("Item Header Check passed");

                Assert.assertTrue(item.getId().getKind().equalsIgnoreCase("youtube#video"));
                Assert.assertTrue(item.getId().getVideoId() != null);
                System.out.println("Id Check passed");

                Assert.assertTrue(item.getSnippet().getChannelId().equals("UC_x5XG1OV2P6uZZ5FSM9Ttw")
                        || item.getSnippet().getChannelId().equals("UCCI6C8hD-hTZi2JEmS7zvQw"));
                Assert.assertTrue(item.getSnippet().getTitle() != null);
                Assert.assertTrue(item.getSnippet().getDescription() != null);
                Assert.assertTrue(item.getSnippet().getChannelTitle() != null);
                Assert.assertTrue(item.getSnippet().getThumbnails() != null);
                System.out.println("Snippet Check passed");

                Assert.assertTrue(item.getSnippet().getThumbnails().getHigh() != null);
                System.out.println("Thumbnail Check passed");

                Assert.assertTrue(item.getSnippet().getThumbnails().getHigh().getHeight() > 0);
                Assert.assertTrue(item.getSnippet().getThumbnails().getHigh().getWidth() > 0);
                Assert.assertTrue(item.getSnippet().getThumbnails().getHigh().getUrl().contains(".jpg"));
                System.out.println("Thumbnail High Check passed");
            }
        }
    }

    private void CHANNEL_TEST(YouTubeImpl youTube) {
        try {
            YouTubeChannelsPage result = youTube.getIdYouTube("Pascal Dierich", range);

            assertTrue(result != null);
            System.out.println("NullPointer Check passed");

            CHECK_FOR_ELEMENTS_CHANNEL(result);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    private void CHECK_FOR_ELEMENTS_CHANNEL(YouTubeChannelsPage page) {
        assertTrue(page.getKind().equalsIgnoreCase("youtube#channelListResponse"));
        assertTrue(!page.getEtag().isEmpty());
        assertTrue(page.getPageInfo() != null);
        System.out.println("Header Check passed");

        assertTrue("totalResults < 0", page.getPageInfo().getTotalResults() >= 0);
        assertTrue("ResultsPerPage != " + range, page.getPageInfo().getResultsPerPage() == range);
        System.out.println("PageInfo Check passed");

        assertTrue(page.getItems() != null);
        System.out.println("Item Check passed");
        System.out.println("Item.size == " + page.getItems().size());

    }

}
