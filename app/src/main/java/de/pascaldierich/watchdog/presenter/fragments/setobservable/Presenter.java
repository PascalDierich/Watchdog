package de.pascaldierich.watchdog.presenter.fragments.setobservable;

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

public class Presenter extends AbstractSetObservablePresenter implements SetObservablePresenter,
        GetIdInteractor.GetIdCallback, StorageInteractor.GetCallback {
    private static final String LOG_TAG = Presenter.class.getSimpleName();
    
    private SetObservablePresenter.View mView;
    
    private Observable mObservable;

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
    
    
    
    /*
        Initial Methods
     */
    
    /**
     * onStart is used to get initialData.
     */
    @Override
    public void onStart() {
        try {
            // pretty shitty
            Observable item = mView.getArgumentsBundle()
                    .getParcelable(mView.getContext().getString(R.string.parcelable_observable));
    
            if (item == null) throw new NullPointerException();
           
            mView.setObservable(item);
    
            super.getSitesInteractor(item.getUserId(), mView.getContext(), this);
        } catch (NullPointerException npe) {
            Log.d("dialog.Presenter", "No transmitted Observable...");
        }
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
    
    
    
    /*
        GetIdInteractor.GetId Callbacks
     */
    
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
    public void onSuccessId(@NonNull ArrayList<Site> result) {
        mSiteArrayList.add(result.get(0));
    
        mView.setTextColor(SupportedNetworks.YOUTUBE,
                ResourcesCompat.getColor(mView.getContext().getResources(), R.color.colorTextVerified, mView.getContext().getTheme()));
        
        Log.i(LOG_TAG, "onSuccess: result.get(0) -> " + result.get(0).getKey());
    }
    
    
    
    /*
        StorageInteractor.Get Callbacks for Sites
     */
    
    /**
     * @param result
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(@NonNull ArrayList<?> result) {
        try {
            super.mSiteArrayList = (ArrayList<Site>) result;
            mView.setSites(super.mSiteArrayList);
        } catch (ClassCastException e) {
            mView.showErrorMessage("" + ModelErrorsCodes.UNKNOWN_FATAL_ERROR); // TODO: 05.03.17 ERROR-ROUTINE (!)
        }
    }
    
    
    
    
    /*
        View Methods
     */
    
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
                        String userInput = mView.getTextNetwork(SupportedNetworks.YOUTUBE);
                        if (userInput == null) throw new NullPointerException();
                        
                        super.getPossibleIds(SupportedNetworks.YOUTUBE, userInput, this);
                    } catch (NullPointerException npe) {
                        mView.showErrorMessage("please verify your input");
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
    
    
    
    /*
        SetObservable Callback from SetObservableActivity
     */
    
    @Override
    public boolean inputVerified() {
        getObservableCallback(); // <-- bit weird order -__(*.*)__-
        return checkObservable() && checkSites();
    }
    
    @Override
    public Observable getObservableCallback() {
        if (mObservable == null) {
            try {
                mObservable = new Observable()
                        .setDisplayName(mView.getTextDisplayName())
                        .setGotThumbnail(false);
                // TODO: 05.03.17 set Thumbnail
        
                return mObservable;
            } catch (NullPointerException npe) {
                return null;
            }
        }
        return mObservable;
    }
    
    @Override
    public ArrayList<Site> getSitesCallback() {
        return super.mSiteArrayList;
    }
    
    
    
    /*
        private Methods
     */
    
    private boolean checkObservable() {
        if (mObservable != null) {
            if (!mObservable.getDisplayName().isEmpty() && !mObservable.getDisplayName().equals("")) {
                return true;
            }
        }
        mView.showErrorMessage("DisplayName can not be empty");
        return false;
    }
    
    private boolean checkSites() {
        return !super.mSiteArrayList.isEmpty();
    }
}
