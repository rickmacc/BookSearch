package org.eightfoldpath.booksearch;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private final String TAG = MainActivity.class.getSimpleName();
    private BookArrayAdapter adapter = null;
    private TextView emptyStateTextView = null;
    private final int LOADER_KEY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.book_list);
        emptyStateTextView = (TextView) findViewById(R.id.empty);
        bookListView.setEmptyView(emptyStateTextView);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.INVISIBLE);
    }

    public void search(View view) {

        Log.d(TAG, "search");

        hideKeyboard(this);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.VISIBLE);

        ListView bookListView = (ListView) findViewById(R.id.book_list);
        bookListView.setVisibility(View.INVISIBLE);

        if (checkNetworkConnectivity()) {
            adapter = new BookArrayAdapter(this, 0, new ArrayList<Book>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            bookListView.setAdapter(adapter);

            getSupportLoaderManager().restartLoader(LOADER_KEY, null, this);
        } else {
            emptyStateTextView.setText(R.string.no_network);
        }

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");

        EditText editText = (EditText) findViewById(R.id.search_box);
        String searchTerms = editText.getText().toString();

        BookLoader loader = new BookLoader(MainActivity.this);
        loader.setQuery(searchTerms);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

        Log.d(TAG, "onLoadFinished");

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.INVISIBLE);

        adapter.clear();
        adapter.setBooks(data);

        if (data.isEmpty()) {
            emptyStateTextView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        Log.d(TAG, "onLoaderReset");
        adapter.setBooks(new ArrayList<Book>());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean checkNetworkConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
