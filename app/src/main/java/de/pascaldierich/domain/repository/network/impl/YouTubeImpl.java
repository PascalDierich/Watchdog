package de.pascaldierich.domain.repository.network.impl;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.IOException;

import de.pascaldierich.domain.repository.network.NetworkRepository;
import de.pascaldierich.model.Model;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

/**
 * Implementation of NetworkRepository.YouTube
 */
public class YouTubeImpl implements NetworkRepository.YouTube {

    /**
     * Search in YouTube Api for the latest Activities
     * for given User.
     * <p>
     * @param id, String: userId defined as key in 'Sites' table
     * @param time, String as RFC3339: publishedAfter Parameter in Api-Request
     * @param range, int: number of maxResults in Api-Response
     * @return TODO write return param
     * @throws IOException
     */
    @Override
    public YouTubeSearchPage searchYouTube(@NonNull String id,
                                           @NonNull String time,
                                           @IntRange(from = 1, to = 50) int range) throws IOException {
        return Model.getInstance().searchYouTube(id, time, range);
    }

    /**
     * Get possibly YouTube-intern-Id's for given name
     * <p>
     * @param name, String: (@WARNING: UserInput) Name of the requested Observable
     * @param range, int: number of maxResults in Api-Response
     * @return TODO write return param
     * @throws IOException
     */
    @Override
    public YouTubeChannelsPage getIdYouTube(@NonNull String name,
                                            @IntRange(from = 1, to = 50) int range) throws IOException {
        return Model.getInstance().getIdYouTube(name, range);
    }
}
