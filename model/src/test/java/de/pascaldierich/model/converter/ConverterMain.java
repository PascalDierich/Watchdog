package de.pascaldierich.model.converter;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import de.pascaldierich.model.Converter;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.GoogleClient;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;
import de.pascaldierich.model.network.services.YouTubeService;
import de.pascaldierich.model.services.youtube.YouTubeServiceSearch;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class ConverterMain {
    private final int range = 5;
    private String time = "2016-01-01T00:00:00Z"; // RFC3339
    private String id = "UC_x5XG1OV2P6uZZ5FSM9Ttw";

    @Test
    public void main() {
        assertTrue(CHECK_POST_YOUTUBE_SEARCH());

    }

    /**
     * Tests <b>getPost(YouTubeSearchPage page, String userId)</b>
     * @return true if success
     */
    private boolean CHECK_POST_YOUTUBE_SEARCH() {
        try {
            YouTubeSearchPage page = GoogleClient.getService(YouTubeService.class)
                    .getVideos(
                            ConstantsApi.YOUTUBE_API_KEY,
                            ConstantsApi.YOUTUBE_SEARCH_PART,
                            id,
                            time,
                            ConstantsApi.YOUTUBE_SEARCH_EVENT_TYPE,
                            range,
                            ConstantsApi.YOUTUBE_SEARCH_ORDER,
                            ConstantsApi.YOUTUBE_SEARCH_TYPE
                    ).execute().body();

            /*
                Test if downloaded data is OK
             */
            YouTubeServiceSearch.CHECK_FOR_ELEMENTS(page);

            Converter mConverter = new Converter();

            try {
                ArrayList<Post> posts = mConverter.getPost(page, id);

                return CHECK_POST_COLLECTION(posts);
            } catch (ModelException exception) {
                System.out.println("Converter Exception: " + exception.getMessage());
                return false;
            }


        } catch (IOException e) {
            System.out.println("IOException: \n");
            e.printStackTrace();
            return false;
        }
    }

    private boolean CHECK_POST_COLLECTION(ArrayList<Post> posts) {
        assertTrue(posts != null);

        System.out.println("posts.size == " + posts.size());

        for (Post post : posts) {
            assertTrue(!post.getGotDownloaded());

        }

        return true;
    }

}
