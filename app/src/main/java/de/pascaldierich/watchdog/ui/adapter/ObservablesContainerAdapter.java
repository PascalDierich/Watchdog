package de.pascaldierich.watchdog.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.ui.Converter;

public class ObservablesContainerAdapter extends RecyclerView.Adapter<ObservablesContainerAdapter.ViewHolder> {

    private ArrayList<Observable> mItems;

    public ObservablesContainerAdapter(ArrayList<Observable> items) {
        mItems = items;
    }

    public void setItems(ArrayList<Observable> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.id.observable_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Observable item = mItems.get(position);
        holder.vDisplayName.setText(item.getDisplayName());

        if (item.getGotThumbnail()) { // if there is a profil pic
            holder.vProfilPic.setImageBitmap( // TODO: 28.02.17 use Picasso
                    Converter.getBitmap(item.getThumbnail()));
        }

//        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profilPic)
        ImageView vProfilPic;
        @BindView(R.id.displayName)
        TextView vDisplayName;


        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}