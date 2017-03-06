package de.pascaldierich.watchdog.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.model.SupportedNetworks;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.R;

/**
 * Adapter for RecyclerView to present Posts
 */
public class PostsContainerAdapter extends RecyclerView.Adapter<PostsContainerAdapter.ViewHolder> {
    
    private ArrayList<Post> mItems;
    private Context mContext;
    
    public PostsContainerAdapter(Context context, @Nullable ArrayList<Post> items) {
        mContext = context;
        mItems = items;
    }
    
    public void setItems(@NonNull ArrayList<Post> items) {
        mItems = items;
        notifyDataSetChanged();
    }
    
    public void addItems(@NonNull ArrayList<Post> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    
        switch (mItems.get(position).getSite()) {
            case SupportedNetworks.YOUTUBE: {
                holder.vIcon.setImageDrawable(mContext.getDrawable(R.drawable.icon_youtube_small));
                break;
            }
            // [...] <-- easy to update for more Networks
        }
    
        // TODO: 06.03.17 add picasso and load with it ThumbanilUrl in vThumbnail
        
        holder.vTitle.setText(mItems.get(position).getTitle());
        holder.vDescription.setText(mItems.get(position).getDescription());
    }
    
    @Override
    public int getItemCount() {
        if (mItems == null)
            return -1;
        else
            return mItems.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layoutPost_title)
        TextView vTitle;
        @BindView(R.id.layoutPost_description)
        TextView vDescription;
        @BindView(R.id.layoutPost_thumbnail)
        ImageView vThumbnail;
        @BindView(R.id.layoutPost_networkIcon)
        ImageView vIcon;
                
        ViewHolder(View itemView) {
            
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
    }
}
