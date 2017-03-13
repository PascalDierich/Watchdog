package de.pascaldierich.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.crash.FirebaseCrash;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.pascaldierich.domain.interactors.storage.Get;
import de.pascaldierich.domain.interactors.storage.StorageInteractor;
import de.pascaldierich.model.ModelErrorsCodes;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.R;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory, StorageInteractor.GetCallback {
    
    Context mContext;
    ArrayList<Post> mCollection = new ArrayList<>();
    private boolean finishedLoading = false;
    
    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }
    
    @Override
    public int getCount() {
        return mCollection.size();
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        
        mView.setTextViewText(android.R.id.text1, mCollection.get(position).getTitle());
        
        return mView;
    }
    
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    
    @Override
    public boolean hasStableIds() {
        return true;
    }
    
    @Override
    public void onCreate() {
        getData();
    }
    
    @Override
    public void onDataSetChanged() {
        if (finishedLoading) {
            finishedLoading = false;
            return;
        }
        getData();
    }
    
    @Override
    public void onDestroy() {
         
    }
    
    
    
    
    
    /*
        private Methods
     */
    
    private void getData() {
        WeakReference<Get> wInteractor = new WeakReference<>(new Get(mContext, this));
        wInteractor.get().getNewsFeed();
    }
    
    
    
    /*
        StorageCallbacks.GetCallbacks
     */
    
    @Override
    public void onFailure(@ModelErrorsCodes int errorCode) {
        FirebaseCrash.log("onFailure: " + errorCode + ". \n" +
                "Storage GetCallback in " + WidgetDataProvider.class.getSimpleName());
        
        finishedLoading = true;
        getManager().notifyAppWidgetViewDataChanged(getManager()
                        .getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class)),
                R.id.widget_postContainer);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(@NonNull ArrayList<?> result) {
        finishedLoading = true;
        try {
            mCollection = (ArrayList<Post>) result;
            
            getManager().notifyAppWidgetViewDataChanged(getManager()
                            .getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class)),
                    R.id.widget_postContainer);
            
        } catch (ClassCastException e) {
            FirebaseCrash.log("ClassCastException: " + e.getMessage() + ". \n" +
                    "Storage GetCallback in " + WidgetDataProvider.class.getSimpleName());
        }
    }
    
    private AppWidgetManager getManager() {
        return AppWidgetManager.getInstance(mContext);
    }
}
