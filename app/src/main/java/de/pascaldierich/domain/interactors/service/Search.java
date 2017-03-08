package de.pascaldierich.domain.interactors.service;

import android.content.Context;
import android.support.annotation.IntRange;
import android.util.Log;

import java.util.ArrayList;

import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;

/**
 * Interactor to search for new Posts.
 * Gets called by WatchdogSyncService.class
 * -> so no need to worry about background Thread
 */
public class Search {
    private String mTime;
    private Context mContext;
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
     * @param time,    String: time last checked for new Posts
     * @param context, WeakReference<Context>: Context to access DB
     * @param range,   int: number of maxResult per Observable per Network
     */
    public Search(String time, Context context, @IntRange(from = 1, to = 50) int range) {
        mTime = time;
        mContext = context;
        mRange = range;
    }
    
    /**
     * run Interactor
     */
    public void run() {
        Log.w("Search", "sync interactor starting...");
        try {
            ArrayList<Site> sites = ApiConnector.getApi().get().getSites(mContext);
            ArrayList<Post> result = new ArrayList<>();
            
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
            // TODO: 08.03.17 make some shitty working algorithm.... >:|
            
            // TODO: implement insertAndThrow method in Provider >:(
            for (int i = 0; i < result.size(); i++) {
                ApiConnector.getApi().get().setNewsFeed(mContext, result.get(i));
            }
        } catch (ModelException modelEx) {
            Log.e("ModelException", "modelEx: " + modelEx.getErrorCode());
            // TODO: 23.02.17 define Error-Routine
        }
    }
    
    public void execute() throws ModelException {
        // throws ModelException, but without Site-Object not reason to go on
        ArrayList<Site> sites = ApiConnector.getApi().get().getSites(mContext);
        ArrayList<Post> result = new ArrayList<>();
    
        for (int i = 0; i < sites.size(); i++) {
            switch (sites.get(i).getSite()) {
                // Check for all SupportedNetworks
                case SupportedNetworks.YOUTUBE: {
                    try {
                        result.addAll(ApiConnector.getApi().get().searchYouTube(
                                sites.get(i).getKey(), sites.get(i).getUserId(), mTime, mRange));
                    } catch (ModelException modelE) {
                        // ErrorCode < 200 means Fatal and Network Errors
                        if (modelE.getErrorCode() < 200) {
                            // TODO: 08.03.17 report Code to Firebase
                        } else {
                            // TODO: 08.03.17 maybe transmit to Analytics. No big deals, but interesting data
                        }
                    }
                    break;
                }
                // [...] <-- insert new Networks
            }
        }
        // TODO: 08.03.17 insert here insertAndThrow method.
    }
}
