package de.pascaldierich.watchdog.ui.adapter;

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
                .inflate(R.layout.container_observable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Observable item = mItems.get(position);
        holder.vDisplayName.setText(item.getDisplayName());

        if (item.getGotThumbnail()) { // if there is a profil pic
            try {
                holder.vProfilPic.setImageBitmap( // TODO: 28.02.17 use Picasso
                        Converter.getBitmap(item.getThumbnail()));
            } catch (NullPointerException npe) { // just in case there is no boolean set
                // do nothing, last statement in void
            }
        }

//        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return -1;
        else
            return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profilPic)
        ImageView vProfilPic;
        @BindView(R.id.displayName)
        TextView vDisplayName;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}