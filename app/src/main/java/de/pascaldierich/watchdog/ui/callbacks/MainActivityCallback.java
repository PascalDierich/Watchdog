package de.pascaldierich.watchdog.ui.callbacks;

import android.support.annotation.NonNull;

import de.pascaldierich.model.domainmodels.Observable;

public interface MainActivityCallback {
    
    void onObservableSelected(@NonNull Observable observable);

}
