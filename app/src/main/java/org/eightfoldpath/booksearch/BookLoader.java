package org.eightfoldpath.booksearch;

import org.eightfoldpath.utils.HttpUtils;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rick on 2/11/17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String TAG = BookLoader.class.getSimpleName();
    private String query = null;

    public BookLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        Log.d(TAG, "loadInBackground - query:" + query);

        return QueryUtils.extractBooks(API_URL + "?maxResults=40&q=" + query);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private static class QueryUtils {

        private QueryUtils() {
        }

        public static ArrayList<Book> extractBooks(String url) {

            ArrayList<Book> books = new ArrayList<Book>();

            try {
                Log.d(TAG, "extractBooks");

                String httpResponse = HttpUtils.makeHttpRequest(HttpUtils.createUrl(url));
                JSONObject response = new JSONObject(httpResponse);

                JSONArray jsonBooks = response.getJSONArray("items");
                for (int i = 0; i < jsonBooks.length(); i++) {
                    JSONObject jsonBook = jsonBooks.getJSONObject(i);
                    JSONObject bookInfo = jsonBook.getJSONObject("volumeInfo");
                    Log.d(TAG, bookInfo.toString());
                    String name = bookInfo.getString("title");
                    String infoUrl = bookInfo.getString("infoLink");
                    String author = "";
                    if (bookInfo.has("authors")) {
                        JSONArray authors = bookInfo.getJSONArray("authors");
                        if (authors.length() > 0) {
                            author = authors.getString(0);
                        }
                    }
                    books.add(new Book(name, infoUrl, author));
                }

            } catch (Exception e) {
                Log.e("QueryUtils", "Problem parsing the book JSON results", e);
            }

            // Return the list of books
            return books;
        }

    }
}
