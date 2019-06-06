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

/**
 * This class contains various static methods useful to handle the communication with the Guardian
 * API.
 */
public class QueryUtils {

    /**
     * This method builds the appropriate URL to reach the desired content in the Guardian API.
     */
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

    /**
     * This method is responsible for creating the http request to the Guardian API.
     *
     * @param pUrl is the URL used to reach the desired content in the Guardian API.
     */
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
                    } catch (IOException e) {
                        Log.e("QueryUtils", e.toString());
                    }
                }
            }
            return response;
        }
    }

    /**
     * This method is responsible for reading the Stream and returning a String with the results.
     */
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
                try {
                    currentLine = bufferedReader.readLine();
                } catch (IOException e) {
                    Log.e("QueryUtils.readStream", e.toString());
                }
            }
        }
        return result.toString();
    }

    /**
     * This method retrieves the appropriate values to create News objects and adding them to an
     * ArrayList of News.
     */
    public static ArrayList<News> parseJson(String pResponse) throws JSONException {
        ArrayList<News> newsList = new ArrayList<>();
        JSONObject response = new JSONObject(pResponse);
        JSONObject results = response.getJSONObject("response");
        JSONArray jsonArray = results.getJSONArray("results");

        // Loop through the JSON Array getting the current JSON object and retrieving the values to
        // appropriately create a News object and adding it to the newsList
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentJsonObject = jsonArray.getJSONObject(i);
            // Retrieve title
            String title = currentJsonObject.getString("webTitle");
            // Retrieve section name
            String section = currentJsonObject.getString("sectionName");
            // If the author is available, retrieve it
            String author = null;
            JSONArray tags = currentJsonObject.getJSONArray("tags");
            int tagsLenght = tags.length();
            if (tagsLenght != 0) {
                for (int j = 0; j < tagsLenght; j++) {
                    JSONObject currentObject = tags.getJSONObject(j);
                    author = currentObject.getString("webTitle");
                }
            }
            // Retrieve date and format it
            String date = currentJsonObject.getString("webPublicationDate");
            date = DateFormatter.format(date);
            // Retrieve url
            String link = currentJsonObject.getString("webUrl");
            // Create a new News object and add it to the newsList
            newsList.add(new News(title, section, author, date, link));
        }
        return newsList;
    }
}
