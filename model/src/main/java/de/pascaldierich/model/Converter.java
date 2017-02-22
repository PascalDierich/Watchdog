package de.pascaldierich.model;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.model.network.models.youtube.channel.YouTubeChannelsPage;
import de.pascaldierich.model.network.models.youtube.search.YouTubeSearchPage;

/**
 * app - model Converter.
 * Get's instantiated by Model.class and converts POJO's for app - model communication.
 */
class Converter {
    
    /*
        Convert to Site
     */
    
    Post getPost(YouTubeSearchPage page) {
        // TODO: 22.02.17 write converter method 
        return null;
    }
    
    Post getPost(YouTubeChannelsPage page) {
        // TODO: 22.02.17 write converter method 
        return null;
    }
    
    /*
        Convert to Site
     */
    Site getSite() {
        
        return null;
    }
    
    /*
        Convert to Observable
     */


}
