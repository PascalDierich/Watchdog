package de.pascaldierich.watchdog.presenter.base;

import android.content.Context;

import java.lang.ref.WeakReference;

public interface BaseView {

    WeakReference<Context> getWeakContext();

    void showError();

}
