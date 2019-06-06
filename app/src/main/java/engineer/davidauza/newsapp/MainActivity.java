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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, SwipeRefreshLayout.OnRefreshListener {

    /**
     * This constant stores the ID of the loader.
     */
    private static final int LOADER_ID = 0;

    /**
     * The RecyclerView to display the list of news.
     */
    private RecyclerView mRecyclerView;

    /**
     * The SwipeRefreshLayout to be able to reload the news.
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSwipeRefreshLayout();
        setUpRecyclerView();
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
            // Hide the no data to display TextView
            TextView noDataTextView = findViewById(R.id.txt_no_data);
            noDataTextView.setVisibility(View.GONE);
            // Set up adapter to the Recycler View
            RecyclerView.Adapter adapter = new NewsAdapter(news, MainActivity.this);
            mRecyclerView.setAdapter(adapter);
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

    /**
     * This method sets up the SwipeRefreshLayout.
     */
    private void setUpSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.ly_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    /**
     * This method sets up the RecyclerView.
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.ly_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
