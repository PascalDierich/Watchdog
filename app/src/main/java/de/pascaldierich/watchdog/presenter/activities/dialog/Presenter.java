package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.domain.interactors.network.GetIdInteractor;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Site;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter,
        GetIdInteractor.GetIdCallback, StorageInteractor.SetCallback {
    private static final String LOG_TAG = Presenter.class.getSimpleName();
    
    private ArrayList<Site> mSiteArrayList;

    private SetObservablePresenter.View mView;

    /*
        Instantiation
     */

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      SetObservablePresenter.View view) {
        super(executor, mainThread, savedInstance);

        mView = view;
        mSiteArrayList = new ArrayList<>();
    }

    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                     SetObservablePresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }

    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        mView.setData(null, null); // TODO: 02.03.17 get Data 
    }

    @Nullable
    private Observable checkTransmittedData() {
        // TODO: 02.03.17 get Data
        // TODO: save tranmitted Sites in mSitesArrayList
        return null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        mView.showError();
        Log.d(LOG_TAG, "onError: errorCode = " + errorCode);
    }
    
    @DebugLog
    @Override
    public void onSaveClicked(String displayName) {
        if (displayName == null || displayName.isEmpty()) mView.showErrorMessage("verify Name");
        
        super.setObservable(
                new Observable()
                        .setDisplayName(displayName)
                        .setGotThumbnail(false),
                mView.getContext(),
                this);
    }
    
    /**
     * Check for id from user-input YouTube Channel name
     * <p/>
     *
     * @param active
     */
    @Override
    public void checkIdYouTube(boolean active) {
        if (!active) {
            // TODO: 03.03.17 delete site from mSitesArrayList and return;
            return;
        }
        try {
            String userInput = mView.getTextYouTube();
            if (userInput == null) throw new NullPointerException();
            
            super.getPossibleIds(SupportedNetworks.YOUTUBE, userInput, this);
        } catch (NullPointerException npe) {
            mView.showErrorMessage("please verify your input"); // TODO: 03.03.17 strings.xml
            return;
        }
    }
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        Log.w(LOG_TAG, "onFailure: (!!!!!!!!!)");
        mView.showErrorMessage("Couldn't find any Channels for user-input");
    }
    
    /**
     * @param result
     */
    @Override
    public void onSuccess(@NonNull ArrayList<Site> result) {
        mSiteArrayList.add(result.get(0));
    
        Log.i(LOG_TAG, "onSuccess: result.get(0) -> " + result.get(0).getKey());
        
        mView.setCheckBoxYouTube(true);
    }
    
    /**
     * Storage-Interactor Callback
     * @param id, long: unique Id for Observable entry
     */
    @DebugLog
    @Override
    public void onSuccess(long id) {
        if (id < 0) return;
        
        super.setObservableId(id);
        super.setSites(mSiteArrayList, mView.getContext(), this);
    }
}
