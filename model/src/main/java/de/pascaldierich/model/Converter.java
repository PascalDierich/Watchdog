package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.model.local.WatchdogContract;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;

/**
 * app - model Converter.
 * Get's instantiated by Model.class and converts POJO's for app - model communication.
 */
public class Converter {
    // This boolean value indicates if there are SQL-specific values (_ID and timestamp)
    private boolean gotDownloaded;

    /**
     * getPost methods
     */

    /**
     * Converts downloaded YouTubeSearch result to Post Collection
     * <p>
     * <b>gotDownloaded = false</b>
     *
     * @param page,   YouTubeSearchPage: API response
     * @param userId, String: network-specific userId
     * @return result, ArrayList<Post>: Collection of all Posts
     * @throws ModelException
     */
    public ArrayList<Post> getPost(@Nullable YouTubeSearchPage page, String userId) throws ModelException {
        ArrayList<Post> result = new ArrayList<>();
        gotDownloaded = false;

        // Because Model.class only gets called after ConnectionTest,
        // this indicates that the channelId is invalid.
        if (page == null) throw new ModelException("Parameter is null");

        // Because the JSON String got converted to POJO correctly (because not null),
        // this indicates that there were no found entries.
        //      --> No new Posts
        if (page.getItems().isEmpty()) throw new ModelException("Parameter holds no data");

        try {
            for (int i = 0; i < page.getItems().size(); i++) {
                result.add(new Post()
                        .setGotDownloaded(gotDownloaded)
                        .setUserId(userId)
                        .setSite(SupportedNetworks.YOUTUBE)
                        .setThumbnailUrl(page.getItems().get(i).getSnippet().getThumbnails().getHigh().getUrl())
                        .setDescription(page.getItems().get(i).getSnippet().getDescription())
                        .setTitle(page.getItems().get(i).getSnippet().getTitle())
                        .setPostId(page.getItems().get(i).getId().getVideoId())
                );
            }
            return result;
        } catch (RuntimeException e) {
            throw new ModelException("Runtime Exception -> " + e.getMessage());
        }
    }

    /**
     * Converts Cursor result to Post Collection
     * <p>
     * <b>gotDownloaded = true</b>
     *
     * @param entries, Cursor: Provider response
     * @return result, ArrayList<Post>: Collection of all given Posts in Cursor
     * @throws ModelException
     */
    public ArrayList<Post> getPost(@Nullable Cursor entries) throws ModelException {
        ArrayList<Post> result = new ArrayList<>();
        gotDownloaded = true;

        if (entries == null) throw new ModelException("Parameter is null");

        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            throw new ModelException("Parameter holds no data");
        }

        do {
            result.add(new Post()
                    .setGotDownloaded(gotDownloaded)
                    .set_ID(entries.getInt(WatchdogContract.Posts.COLUMN_ID_ID))
                    .setUserId(entries.getString(WatchdogContract.Posts.COLUMN_USER_ID_ID))
                    .setThumbnailUrl(entries.getString(WatchdogContract.Posts.COLUMN_THUMBNAIL_URL_ID))
                    .setDescription(entries.getString(WatchdogContract.Posts.COLUMN_DESCRIPTION_ID))
                    .setTitle(entries.getString(WatchdogContract.Posts.COLUMN_TITLE_ID))
                    .setPostId(entries.getString(WatchdogContract.Posts.COLUMN_POST_ID_ID))
                    .setSite(entries.getString(WatchdogContract.Posts.COLUMN_SITE_ID))
                    .setTimestamp(entries.getString(WatchdogContract.Posts.COLUMN_TIMESTAMP_ID))
            );
        } while (entries.moveToNext());

        return result;
    }
    

    /**
     * getSite methods
     */

    /**
     * Converts downloaded YouTubeChannel result to Site Collection
     * <p>
     * <b>gotDownloaded = false</b>
     *
     * @param page YouTubeSearchPage: API response
     * @return result, ArrayList<Site>: Collection of all Sites
     * @throws ModelException
     */
    public ArrayList<Site> getSite(@Nullable YouTubeChannelsPage page) throws ModelException {
        ArrayList<Site> result = new ArrayList<>();
        gotDownloaded = false;

        // Because Model.class only gets called after ConnectionTest,
        // this indicates that the channelId is invalid.
        if (page == null) throw new ModelException("Parameter is null");

        // Because the JSON String got converted to POJO correctly (because not null),
        // this indicates that there were no found entries.
        //      --> No new Posts
        if (page.getItems().isEmpty()) throw new ModelException("Parameter holds no data");

        try {
            for (int i = 0; i < page.getItems().size(); i++) {
                result.add(new Site()
                        .setGotDownloaded(gotDownloaded)
                        .setSite(SupportedNetworks.YOUTUBE)
                        .setKey(page.getItems().get(i).getId())
                );
            }
            return result;
        } catch (RuntimeException e) {
            throw new ModelException("Runtime Exception -> " + e.getMessage());
        }
    }


    /**
     * getObservable methods
     */

    /**
     * Converts Cursor result to Observable Collection
     * <p>
     *
     * @param entries, Cursor: Provider response
     * @return result, ArrayList<Observable>: Collection of all given Observables in Cursor
     * @throws ModelException
     */
    public ArrayList<Observable> getObservables(@Nullable Cursor entries) throws ModelException {
        ArrayList<Observable> result = new ArrayList<>();

        if (entries == null) throw new ModelException("Parameter is null");

        try {
            entries.moveToFirst();
        } catch (SQLException e) {
            throw new ModelException("Parameter holds no data");
        }

        do {
            Observable item = new Observable();
            item.setUserId(entries.getInt(WatchdogContract.Observables.COLUMN_USER_ID_ID))
                    .setDisplayName(entries.getString(WatchdogContract.Observables.COLUMN_NAME_ID));

            try {
                item.setThumbnail(entries.getBlob(WatchdogContract.Observables.COLUMN_THUMBNAIL_ID));
                item.setGotThumbnail(true);
            } catch (SQLException e) {
                item.setGotThumbnail(false);
            }
            result.add(item);
        } while (entries.moveToNext());

        return result;
    }

}
