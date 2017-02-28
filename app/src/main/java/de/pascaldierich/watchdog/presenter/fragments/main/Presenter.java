package de.pascaldierich.watchdog.presenter.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import de.pascaldierich.domain.executor.Executor;
import de.pascaldierich.domain.executor.MainThread;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.presenter.base.ErrorPresenter;
import hugo.weaving.DebugLog;

public class Presenter extends AbstractObservableListPresenter
        implements ObservableListPresenter, de.pascaldierich.domain.interactors.storage.StorageInteractor.GetCallback {

    private ObservableListPresenter.View mView;

    /*
        Instantiation
     */

    private Presenter(Executor executor, MainThread mainThread, Bundle savedInstance,
                      ObservableListPresenter.View view) {
        super(executor, mainThread, savedInstance);

        mView = view;
    }

    public static Presenter onCreate(Executor executor, MainThread mainThread, Bundle savedInstance,
                                    ObservableListPresenter.View view) {
        return new Presenter(executor, mainThread, savedInstance, view);
    }

    @DebugLog
    @Override
    public void onStart() {
        // read out intern Storage to get all Observables
        super.getObservables(mView.getWeakContext(), this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        // TODO: 27.02.17 define Error-Codes
        onError(-1);
    }

    /**
     * @param result
     */
    @Override
    public void onSuccess(@NonNull ArrayList<?> result) {
        mView.setData((ArrayList<Observable>) result);
    }

    /**
     * @param errorCode
     */
    @Override
    public void onError(@ErrorPresenter int errorCode) {
        mView.showError();
    }

    /**
     *
     * @param observableId, int: unique Observables identifier
     */
    @Override
    public void onObservableSelected(int observableId) {
        // TODO: 27.02.17 define keys in strings.xml
        Bundle bundle = new Bundle();
        for (int i = 0; i < mObservables.size(); i++) {
            if (mObservables.get(i).getUserId() == observableId) {
                bundle.putString("displayName", mObservables.get(i).getDisplayName());
                bundle.putInt("observableId", observableId);

                if (mObservables.get(i).getGotThumbnail()) {
                    bundle.putByteArray("thumbnail", mObservables.get(i).getThumbnail());
                    bundle.putBoolean("gotThumbnail", true);
                } else {
                    bundle.putBoolean("gotThumbnail", false);
                }
                break;
            }
        }

        mView.startActivity(new Intent().putExtra("Observable", bundle));
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
}
