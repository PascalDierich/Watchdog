package de.pascaldierich.watchdog.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.watchdog.R;

/**
 * Adapter for RecyclerView to present Posts
 */
public class PostsContainerAdapter extends RecyclerView.Adapter<PostsContainerAdapter.ViewHolder> {
    
    private ArrayList<Post> mItems;
    
    public PostsContainerAdapter(@Nullable ArrayList<Post> items) {
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
                .inflate(R.layout.container_posts, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: 04.03.17 present all information
        
        holder.vTestTextView.setText(mItems.get(position).getTitle());
        
    }
    
    @Override
    public int getItemCount() {
        if (mItems == null)
            return -1;
        else
            return mItems.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.test_NotUseTextView)
        TextView vTestTextView;
        
        
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
    }
}
