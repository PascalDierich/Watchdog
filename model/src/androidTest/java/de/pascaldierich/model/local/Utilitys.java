package de.pascaldierich.model.local;

import android.content.ContentValues;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class Utilitys {
    
    static ContentValues[] createSitesValues() {
        ContentValues[] values = new ContentValues[5];
        String[] sites = {
                "YouTube",
                "Plus",
                "Plus",
                "YouTube",
                "YouTube"
        };
        
        String[] keys = {
                "/fjrkwnv",
                "/vmwvwev",
                "/vuonseiuv",
                "/ovijv",
                "/vklmrevlrmev"
        };
        
        int a;
        for (int i = 0; i < 5; i++) {
            values[i] = new ContentValues();
            a = i;
            values[i].put("_ID", ++a);
            values[i].put("site", sites[i]);
            values[i].put("key", keys[i]);
        }
        return values;
    }
    
    static ContentValues[] createObservablesValues() {
        ContentValues[] values = new ContentValues[5];
        String[] names = {
                "Pascal Dierich",
                "Bill Gates",
                "Hans Peter",
                "Mark Zuckerberg",
                "Jesus Christ"
        };
        
        for (int i = 0; i < 5; i++) {
            values[i] = new ContentValues();
            values[i].put("displayName", names[i]);
            values[i].put("thumbnail", new byte[] {});
        }
        return values;
    }
    
}
