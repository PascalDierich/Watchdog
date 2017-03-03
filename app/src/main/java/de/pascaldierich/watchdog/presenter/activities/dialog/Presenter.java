package de.pascaldierich.watchdog.presenter.activities.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
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
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter,
        GetIdInteractor.GetIdCallback, StorageInteractor.SetCallback {
    private static final String LOG_TAG = Presenter.class.getSimpleName();
    
    private SetObservablePresenter.View mView;

    /*
        Instantiation
     */
    
    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      SetObservablePresenter.View view) {
        super(executor, mainThread, savedInstance);
        
        mView = view;
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
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        mView.setTextColor(SupportedNetworks.YOUTUBE,
                ResourcesCompat.getColor(mView.getContext().getResources(), R.color.colorTextError, mView.getContext().getTheme()));
        
        mView.showErrorMessage("Couldn't find any Channels for user-input");
    }
    
    /**
     * @param result
     */
    @Override
    public void onSuccess(@NonNull ArrayList<Site> result) {
        mSiteArrayList.add(result.get(0));
    
        mView.setTextColor(SupportedNetworks.YOUTUBE,
                ResourcesCompat.getColor(mView.getContext().getResources(), R.color.colorTextVerified, mView.getContext().getTheme()));
        
        Log.i(LOG_TAG, "onSuccess: result.get(0) -> " + result.get(0).getKey());
        
        // TODO: 03.03.17 change color of Text to indicate success
    }
    
    /**
     * Storage-Interactor Callback
     * <p/>
     * @param id, long: unique Id for Observable entry
     */
    @DebugLog
    @Override
    public void onSuccess(long id) {
        if (id < 0) return;
        
        super.setObservableId(id);
        super.setSites(mSiteArrayList, mView.getContext(), this);
    }
    
    /**
     * Gets called when switch state changes.
     * checked = true -> check user-input and get Id
     * checked = false -> delete (if exists) Site-Object from Collection.
     * Both states changes the TextColor for UX too.
     * <p/>
     *
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param checked, boolean: true if checked, false if not
     */
    @Override
    public void onStateChanged(@SupportedNetworks String network, boolean checked) {
        switch (network) {
            case SupportedNetworks.YOUTUBE: {
                mView.setTextColor(SupportedNetworks.YOUTUBE,
                        ResourcesCompat.getColor(mView.getContext().getResources(), R.color.colorTextDisabled, mView.getContext().getTheme()));
                
                if (checked) {
                    try {
                        String userInput = mView.getTextNetwork(SupportedNetworks.YOUTUBE); // TODO: 03.03.17 change Method to give Site as Parameter
                        if (userInput == null) throw new NullPointerException();
                        
                        super.getPossibleIds(SupportedNetworks.YOUTUBE, userInput, this);
                    } catch (NullPointerException npe) {
                        mView.showErrorMessage("please verify your input");// TODO: 03.03.17 strings.xml
                    }
                } else {
                    for (int i = 0; i < super.mSiteArrayList.size(); i++) {
                        if (mSiteArrayList.get(i).getSite().equalsIgnoreCase(SupportedNetworks.YOUTUBE)) {
                            mSiteArrayList.remove(i);
                            break;
                        }
                    }
                }
                break;
            }
            // [..] <- someday there will occur more supported Networks
        }
    }
    
    /**
     * Gets called when Network is activated but input got changed.
     * <p/>
     *
     * @param network, String: Name of Network (@SupportedNetworks)
     * @param newInput, String: new User input to check
     */
    @Override
    public void onInputChanged(@SupportedNetworks String network, @Nullable String newInput) {
        if (newInput == null || newInput.isEmpty()) {
            return;
        }
    
        switch (network) {
            case SupportedNetworks.YOUTUBE: {
                mView.setTextColor(SupportedNetworks.YOUTUBE,
                        ResourcesCompat.getColor(mView.getContext().getResources(), R.color.colorTextDisabled, mView.getContext().getTheme()));
                
                super.getPossibleIds(SupportedNetworks.YOUTUBE, newInput, this);
                break;
            }
            // [..] <- someday...
        }
    }
}
