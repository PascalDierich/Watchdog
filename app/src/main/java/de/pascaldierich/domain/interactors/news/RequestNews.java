package de.pascaldierich.domain.interactors.news;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import java.util.ArrayList;

import de.pascaldierich.domain.repository.network.impl.YouTubeImpl;
import de.pascaldierich.model.domainmodels.Post;

/**
 * Calls all 'search' methods defined in 'repository.network'.
 * Gets called by SyncService class.
 * <p>
 * Does not extend AbstractInteractor.class because SyncService already runs in BackgroundThread.
 */
public class RequestNews {

    // Repositories defined in package network
    private YouTubeImpl mYouTube;

    public RequestNews() {
        mYouTube = new YouTubeImpl();
    }

    /**
     * Start method for Interactors.
     * @return ArrayList<Post>: all new Posts {@see domain.models.Post}
     */
    public ArrayList<Post> run() {
        // TODO: 21.02.17
        // 1. get Sites Table information to perform Request.
        // 2. perform Requests and return ArrayList

        return null;
    }

}
