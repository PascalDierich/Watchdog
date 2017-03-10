package de.pascaldierich.watchdog.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.posts.PostPresenter;
import de.pascaldierich.watchdog.presenter.fragments.posts.Presenter;
import de.pascaldierich.watchdog.ui.adapter.PostsContainerAdapter;

public class PostsFragment extends Fragment implements PostPresenter.View {
    
    /*
        Instantiation
     */
    
    private Presenter mPresenter;
    private View mRootView;
    
    private PostsFragment.ImplicitIntentCallback mCallback;
    
    // boolean to work with Paging. true -> NewsFeed, false -> Favorites
    private boolean mSelectedPage = true;
    
    /* Layout */
    @BindView(R.id.posts_container)
    RecyclerView mPostsContainer;
    
    private PostsContainerAdapter mAdapter;
    
    
    
    /*
        Initial Methods
     */
    
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstance, this);
    
        mAdapter = new PostsContainerAdapter(getContext(), null, mPresenter);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        mRootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, mRootView);
        
        mPostsContainer.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mPostsContainer.setAdapter(mAdapter);
        
        return mRootView;
    }
    
    // TODO: 07.03.17 DEPRECATED!
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ImplicitIntentCallback) activity;
        
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void showError() {
        Toast.makeText(getContext(), "UnknownError", Toast.LENGTH_SHORT).show();
    }
    
    
    
    /*
        Menu Methods
     */
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_selection_menu, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showFavorites: {
                mSelectedPage = false;
                mPresenter.onPageChanged(mSelectedPage);
                break;
            }
            case R.id.showNewsFeed: {
                mSelectedPage = true;
                mPresenter.onPageChanged(mSelectedPage);
                break;
            }
        }
        
        return true;
    }
    
    
    
    
    /*
        View-Methods for Presenter
     */
    
    /**
     * returns the args the fragment got created with.
     *      <b>Note:</b> key := R.string.parcelable_observable
     * <p/>
     *
     * @return args, Bundle: contains an Observable-Object
     */
    @NonNull
    @Override
    public Bundle getArgumentsBundle() {
        return this.getArguments();
    }
    
    /**
     * show Posts (either NewsFeed or Favorites)
     *
     * @param posts
     */
    @Override
    public void setData(ArrayList<Post> posts) {
        mAdapter.setItems(posts);
    }
    
    /**
     * @return selectedPage, boolean: true -> NewsFeed; false -> Favorites
     */
    @Override
    public boolean getSelectedPage() {
        return mSelectedPage;
    }
    
    /**
     * shows the current Observable in included default layout.
     * <p/>
     *
     * @param observable
     */
    @Override
    public void showObservable(Observable observable) {
        IncludedObservableLayoutHolder holder = new IncludedObservableLayoutHolder(mRootView);
        
        holder.mObservableDisplayName.setText(observable.getDisplayName());
           
    }
    
    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    /*
        Method to start/update activities/fragment
     */
    
    @Override
    public void sendIntentToActivity(Intent intent) {
        mCallback.onStartIntent(intent);
    }
    
    
    
    /*
        Callback Interfaces
     */
    
    /**
     *
     */
    public interface ImplicitIntentCallback {
    
        void onStartIntent(@NonNull Intent intent);
        
    }
    
    
    
    /*
        static class for ViewHolder
     */
    
    static class IncludedObservableLayoutHolder {
        // included Observable Default Layout
        @BindView(R.id.included_observable_default_layout)
        View mObservableRootView;
        @BindView(R.id.layoutObservable_thumbnail)
        ImageView mObservableImageView;
        @BindView(R.id.layoutObservable_displayName)
        TextView mObservableDisplayName;
            // Icons, @see {layout_observable_big.xml}
    
        public IncludedObservableLayoutHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
