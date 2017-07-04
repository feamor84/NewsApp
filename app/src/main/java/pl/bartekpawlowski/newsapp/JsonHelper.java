package pl.bartekpawlowski.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Helper class to handle JSON parsing
 */

public final class JsonHelper {

    private static final String LOG_TAG = "JSON helper";

    private JsonHelper() {};

    public static ArrayList<News> makeNewsArrayList(String query) {
        ArrayList<News> newsList = new ArrayList<News>();
        String jsonString = "";

        try {
            jsonString = HttpHelper.makeHttpRequest(query);
            Log.i(LOG_TAG, "JSON response: " + jsonString);
            if (jsonString != null) {
                JSONObject root = new JSONObject(jsonString);
                JSONObject response = root.optJSONObject("response");
                JSONArray results = response.optJSONArray("results");
                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        News tmp = new News(
                                result.getString("webTitle"),
                                result.getString("sectionName"),
                                result.getString("webPublicationDate"),
                                result.getString("webUrl")
                        );
                        newsList.add(tmp);
                    }
                }
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during JSON parsing", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error during JSON parsing", e);
        }

        return newsList;
    }
}
