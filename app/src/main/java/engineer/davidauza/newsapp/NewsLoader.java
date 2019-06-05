package engineer.davidauza.newsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

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
        // TODO check the way to manage the ArrayList
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
