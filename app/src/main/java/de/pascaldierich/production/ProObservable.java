package de.pascaldierich.production;

import android.content.Context;

import java.util.ArrayList;

import de.pascaldierich.domain.repository.ApiConnector;
import de.pascaldierich.model.ModelException;
import de.pascaldierich.model.domainmodels.Observable;

public abstract class ProObservable {
    private static String[] displayNames = {
            "Pascal Dierich",
            "Bill Gates",
            "Mark Zuckerberg",
            "Jesus Christ",
            "Hans Müller",
            "Peter Peters",
            "dvvdsvdsvsd",
            "sdvsdvdsvds",
            "dvsdvsdvdsvsd",
            "vsdvsdvsdv"
    };
    
    public static void addObservables(Context context) throws ModelException {
        for (Observable mItem : createObservables()) {
            ApiConnector.getApi().get().setObservable(context, mItem);
        }
    }
    
    public static void removeObservables(Context context) throws ModelException {
        ApiConnector.getApi().get().removeObservable(context);
    }
    
    private static ArrayList<Observable> createObservables() {
        ArrayList<Observable> result = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            result.add(new Observable()
                    .setDisplayName(displayNames[i])
                    .setGotThumbnail(false));
        }
        
        return result;
    }
}
