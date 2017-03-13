package de.pascaldierich.domain.interactors.base;

import de.pascaldierich.model.ModelErrorsCodes;

public interface BaseCallback {
    
    void onFailure(@ModelErrorsCodes int errorCode);
    
}
