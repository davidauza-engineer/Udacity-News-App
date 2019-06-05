package engineer.davidauza.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    // TODO pending check
    public static URL buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").encodedAuthority("content.guardianapis.com").appendPath("search").
                appendQueryParameter("oder-by", "newest").
                appendQueryParameter("show-referemces", "author").
                appendQueryParameter("q", "Colombia").appendQueryParameter("api-key", "test").
                appendQueryParameter("show-tags", "contributor");
        String link = builder.build().toString();
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", e.toString());
            return null;
        }
    }

    public static String httpRequest(URL pUrl) {
        String response = "";

        if (pUrl == null) {
            return response;
        } else {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try {
                connection = (HttpURLConnection) pUrl.openConnection();
                connection.setRequestMethod("GET");
                // TODO verify order here
                // TODO method set connection?
                connection.setConnectTimeout(16000);
                connection.setReadTimeout(8000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    inputStream = connection.getInputStream();
                    response = readStream(inputStream);
                } else {
                    Log.e("QueryUtils", "Error: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e("QueryUtils", e.toString());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        // TODO method?
                    } catch (IOException e) {
                        Log.e("QueryUtils", e.toString());
                    }
                }
            }
            return response;
        }
    }

    private static String readStream(InputStream pInputStream) {
        StringBuilder result = new StringBuilder();
        if (pInputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(pInputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String currentLine = null;
            try {
                currentLine = bufferedReader.readLine();
            } catch (IOException e) {
                Log.e("QueryUtils", e.toString());
            }
            while (currentLine != null) {
                result.append(currentLine);
                // TODO method try catch
                try {
                    currentLine = bufferedReader.readLine();
                } catch (IOException e) {
                    Log.e("readStream", e.toString());
                }
            }
        }
        return result.toString();
    }

    public static ArrayList<News> parseJson(String pResponse) throws JSONException {
        ArrayList<News> newsList = new ArrayList<>();
        JSONObject response = new JSONObject(pResponse);
        JSONObject results = response.getJSONObject("response");
        JSONArray jsonArray = results.getJSONArray("results");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentResult = jsonArray.getJSONObject(i);
            String title = currentResult.getString("webTitle");
            String section = currentResult.getString("sectionName");
            String author = null;
            JSONArray tags = currentResult.getJSONArray("tags");
            int tagsLenght = tags.length();
            if (tagsLenght != 0) {
                for (int j = 0; j < tagsLenght; j++) {
                    JSONObject currentObject = tags.getJSONObject(j);
                    // TODO check this!
                    author += currentObject.getString("webTitle");
                }
            }
            // TODO check date format
            String date = currentResult.getString("webPublicationDate");
            String link = currentResult.getString("webUrl");
            newsList.add(new News(title, section, author, date, link));
        }
        return newsList;
    }
}
