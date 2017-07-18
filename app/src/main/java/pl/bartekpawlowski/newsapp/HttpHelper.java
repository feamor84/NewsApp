package pl.bartekpawlowski.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Helper class to handle HTTP request
 */

public final class HttpHelper {

    private final static String API_KEY = "test";
    private final static String GUARDIAN_URL = "https://content.guardianapis.com/search";
    private final static String LOG_TAG = "HTTP helper";
    private final static int TIMEOUT = 1000;

    private HttpHelper() {};

    private static String urlBuilder(String search) {
        Uri baseUri = Uri.parse(GUARDIAN_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", search);
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.i(LOG_TAG, uriBuilder.toString());

        return uriBuilder.toString();
    }

    private static URL createUrl(String baseUri) {
        URL url = null;
        try {
            url = new URL(baseUri);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error parsing URL", e);
        }

        return url;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuffer output = new StringBuffer();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    public static String makeHttpRequest(String urlString) throws IOException {
        URL url = createUrl(urlBuilder(urlString));
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(TIMEOUT);
            httpURLConnection.setReadTimeout(TIMEOUT);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);

                Log.i(LOG_TAG, "Request handle with response: " + jsonResponse);
            } else {
                Log.e(LOG_TAG, "Server reject request with code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during HTTP request", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }
}
