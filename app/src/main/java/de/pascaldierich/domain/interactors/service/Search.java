package de.pascaldierich.domain.interactors.service;

import android.content.Context;
import android.support.annotation.IntRange;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;

/**
 * Interactor to search for new Posts.
 * Gets called by TODO: add Service name
 *      -> so no need to worry about background Thread
 */
public class Search {
    private String mTime;
    private WeakReference<Context> wContext;
    private int mRange;

    /*
    1. get all Sites
    2. for each Site / key call Model
    3. save all new Sites in NewsFeed
     */

    /**
     * Constructor
     * <p>
     *
     * @param time, String: time last checked for new Posts
     * @param context, WeakReference<Context>: Context to access DB
     * @param range, int: number of maxResult per Observable per Network
     */
    public Search(String time, WeakReference<Context> context, @IntRange(from = 1, to = 50) int range) {
        mTime = time;
        wContext = context;
        mRange = range;
    }

    /**
     * run Interactor
     */
    public void run() {
        try {
            ArrayList<Site> sites = ApiConnector.getApi().get().getSites(wContext.get());
            ArrayList<Post> result = new ArrayList<>();
            Site site;

            for (int i = 0; i < sites.size(); i++) {
                switch (sites.get(i).getSite()) {
                    // Check for all SupportedNetworks
                    case SupportedNetworks.YOUTUBE: {
                        result.addAll(ApiConnector.getApi().get().searchYouTube(
                                sites.get(i).getKey(), sites.get(i).getUserId(), mTime, mRange));
                        break;
                    }
                }
            }

            // TODO: implement insertAndThrow method in Provider >:(
            for (int a = 0; a < result.size(); a++) {
                ApiConnector.getApi().get().setNewsFeed(wContext.get(), result.get(a));
            }
        } catch (ModelException modelEx) {
            // TODO: 23.02.17 define Error-Routine
        }
    }
}
