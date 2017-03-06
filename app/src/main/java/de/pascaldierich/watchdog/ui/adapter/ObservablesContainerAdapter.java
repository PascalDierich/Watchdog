package de.pascaldierich.watchdog.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.ui.Converter;

/**
 * Adapter for RecyclerView to present the Observables
 */
public class ObservablesContainerAdapter extends RecyclerView.Adapter<ObservablesContainerAdapter.ViewHolder> {
    
    private Context mContext;
    private ArrayList<Observable> mItems;
    private ObservablesContainerAdapter.AdapterCallback mCallback;
    
    public ObservablesContainerAdapter(Context context, ArrayList<Observable> items,
                                       ObservablesContainerAdapter.AdapterCallback callback) {
        mContext = context;
        mItems = items;
        mCallback = callback;
    }
    
    public void setItems(ArrayList<Observable> items) {
        mItems = items;
        notifyDataSetChanged();
    }
    
    public void addItems(ArrayList<Observable> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_observable_big, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Observable item = mItems.get(position);
        
        
        holder.vDisplayName.setText(item.getDisplayName());
        holder.vIconYouTube.setImageDrawable(mContext.getDrawable(R.drawable.icon_youtube_small));
        
        if (item.getGotThumbnail()) {
            try {
                holder.vThumbnail.setImageBitmap( // TODO: 28.02.17 use Picasso
                        Converter.getBitmap(item.getThumbnail()));
            } catch (NullPointerException npe) { // just in case there is no boolean set
                
            }
        }
        
        holder.vLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Callback to MainActivity
                mCallback.onCardViewClick(holder.getAdapterPosition());
            }
        });
    }
    
    @Override
    public int getItemCount() {
        if (mItems == null)
            return -1;
        else
            return mItems.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_observable_big)
        CardView vLayout;
        @BindView(R.id.layoutObservable_thumbnail)
        ImageView vThumbnail;
        @BindView(R.id.layoutObservable_displayName)
        TextView vDisplayName;
        @BindView(R.id.layoutObservable_iconPreview_youtube)
        ImageView vIconYouTube;
        
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
    /**
     * Interface which got implemented by fragment.main.Presenter (ObservableListFragment's Presenter).
     */
    public interface AdapterCallback {
        
        void onCardViewClick(int position);
    
    }
}