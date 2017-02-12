package org.eightfoldpath.booksearch;

import org.eightfoldpath.utils.HttpUtils;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rick on 2/11/17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private static final String API_URL = "http://book.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=book&orderby=time&minmag=6&limit=10";
    private static final String TAG = BookLoader.class.getSimpleName();

    public BookLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        Log.d(TAG, "loadInBackground()");
        return QueryUtils.extractBooks(API_URL);
    }

    /**
     * Helper methods related to requesting and receiving book data from USGS.
     */
    private static class QueryUtils {

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtils() {
        }

        /**
         * Return a list of {@link Book} objects that has been built up from
         * parsing a JSON response.
         */
        public static ArrayList<Book> extractBooks(String url) {

            // Create an empty ArrayList that we can start adding books to
            ArrayList<Book> books = new ArrayList<Book>();
            books.add(new Book("The Old Man and The Sea"));

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                Log.d("QueryUtils", "extractBooks");

                String httpResponse = HttpUtils.makeHttpRequest(HttpUtils.createUrl(url));
                JSONObject response = new JSONObject(httpResponse);

                /*
                JSONArray jsonquakes = response.getJSONArray("features");
                for (int i = 0; i < jsonquakes.length(); i++) {
                    JSONObject jsonquake = jsonquakes.getJSONObject(i);
                    Log.d(TAG, jsonquake.toString());
                    JSONObject properties = jsonquake.getJSONObject("properties");
                    String magnitude = properties.getString("mag");
                    String place = properties.getString("place");
                    String time = properties.getString("time");
                    String quake_url = properties.getString("url");
                    books.add(new Book(Double.valueOf(magnitude), place, new Date(Long.parseLong(time)), quake_url));
                }
                */

            } catch (Exception e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the book JSON results", e);
            }

            // Return the list of books
            return books;
        }

    }
}
