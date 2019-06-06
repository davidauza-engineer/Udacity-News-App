package engineer.davidauza.newsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

/**
 * This class is responsible for loading the News and returning an ArrayList which contains the
 * news to display.
 */
public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /**
     * Create a new {@link NewsLoader} object.
     *
     * @param pContext is the context of the MainActivity.
     */
    public NewsLoader(Context pContext) {
        super(pContext);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        // Create the ArrayList which should contain the news.
        ArrayList<News> newsList = null;
        try {
            URL link = QueryUtils.buildUrl();
            String response = QueryUtils.httpRequest(link);
            newsList = QueryUtils.parseJson(response);
        } catch (JSONException e) {
            Log.e("NewsLoader", e.toString());
        }
        return newsList;
    }
}
