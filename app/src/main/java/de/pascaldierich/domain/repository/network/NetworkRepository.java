package de.pascaldierich.domain.repository.network;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import java.io.IOException;

import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;

/**
 * Boundaries for model.network.services and interactors
 * Defines Methods for interacting with the Data Layer to request Api-Data
 */
public interface NetworkRepository {

    /**
     * YouTube Api
     */
    interface YouTube {

        /**
         * Search in YouTube Api for the latest (@param time)
         * X (@param range) Activities for given User (@param id).
         * @param id, String: userId defined as key in 'Sites' table
         * @param time, String as RFC3339: publishedAfter Parameter in Api-Request
         * @param range, int: number of maxResults in Api-Response
         * @return YouTubeSearchPage: model-POJO for interactors
         */
        YouTubeSearchPage searchYouTube(String id, String time, int range) throws IOException;

        /**
         * Get X (@param range) results for userId request
         * from YouTube for given Name (@param name)
         * @param name, String: (@WARNING: UserInput) Name of the requested Observable
         * @param range, int: number of maxResults in Api-Response
         * @return YouTubeChannelsPage: model-POJO for interactors
         */
        YouTubeChannelsPage getIdYouTube(String name, int range) throws IOException;
    }

}
