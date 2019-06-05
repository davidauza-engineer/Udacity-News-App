package engineer.davidauza.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOADER_ID = 0;
    // TODO check global variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSwipeRefreshLayout();
        setUpRecyclerView();
        // TODO remember to create the default text view if it is not posible to load news
        // TODO check if it is worth to create a method for this
        getSupportLoaderManager().
                initLoader(LOADER_ID, null, MainActivity.this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> news) {
        swipeRefreshLayout.setRefreshing(false);
        if (news != null) {
            // TODO resume here!
            // TODO check!
            // TODO method?
            // Set up adapter to the Recycler View
            mAdapter = new NewsAdapter(news);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().
                restartLoader(LOADER_ID, null, MainActivity.this);
    }

    private void setUpSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.ly_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.ly_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
