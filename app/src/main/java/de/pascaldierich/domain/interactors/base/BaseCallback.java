package de.pascaldierich.domain.interactors.base;

import de.pascaldierich.model.ModelErrorsCodes;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

public interface BaseCallback {

    void onFailure(@ModelErrorsCodes int errorCode);

}
