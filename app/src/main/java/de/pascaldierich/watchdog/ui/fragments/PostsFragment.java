package de.pascaldierich.watchdog.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pascaldierich.domain.executor.impl.ThreadExecutor;
import de.pascaldierich.model.domainmodels.Observable;
import de.pascaldierich.model.domainmodels.Post;
import de.pascaldierich.threading.MainThreadImpl;
import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.presenter.fragments.posts.Presenter;
import de.pascaldierich.watchdog.ui.adapter.PostsContainerAdapter;

public class PostsFragment extends Fragment implements Presenter.View {
    
    private Presenter mPresenter;
    private View mRootView;
    
    private Observable mObservable;
    
    private PostsFragment.ImplicitIntentCallback mCallback;
    
    // boolean to work with Paging. true -> NewsFeed, false -> Favorites
    private boolean mSelectedPage = true;
    
    /* Layout */
    @BindView(R.id.posts_container)
    RecyclerView mPostsContainer;
    
    private PostsContainerAdapter mAdapter;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ImplicitIntentCallback) activity;
        
    }
    
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    
        mPresenter = Presenter.onCreate(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                savedInstance, this);
        
        mPresenter.setObservable(mObservable);
        mObservable = null;
    
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
    
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }
    
    /**
     *
     * @param observable
     * @return this, PostFragment
     */
    public PostsFragment setObservable(Observable observable) {
        mObservable = observable;
        return this;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void showError() {
        Toast.makeText(getContext(), "UnknownError", Toast.LENGTH_SHORT).show();
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
    
    
    @Override
    public void sendIntentToActivity(Intent intent) {
        
    }
    
    
    public interface ImplicitIntentCallback {
    
        void onStartIntent(@NonNull Intent intent);
        
    }
    
    
}
