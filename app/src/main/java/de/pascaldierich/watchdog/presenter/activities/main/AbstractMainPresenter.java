package de.pascaldierich.watchdog.presenter.activities.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.activities.AbstractSetObservablePresenter;
import de.pascaldierich.watchdog.presenter.base.AbstractPresenter;

public abstract class AbstractMainPresenter extends AbstractSetObservablePresenter {
    
    
    /**
     * @see {@link AbstractPresenter}
     */
    public AbstractMainPresenter(Executor executor, MainThread mainThread, @Nullable Bundle savedInstance) {
        super(executor, mainThread, savedInstance);
    }
    
    public void deleteObservable(Observable item, Context context) {
        // TODO: 06.03.17 write delete interacto
    }
    
}
