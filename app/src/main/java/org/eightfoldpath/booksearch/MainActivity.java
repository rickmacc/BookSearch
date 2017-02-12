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

    private final String TAG = "MainActivity";
    private final String BOOK_API = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private BookArrayAdapter adapter = null;
    private TextView emptyStateTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.INVISIBLE);
    }

    public void search(View view) {

        EditText editText = (EditText) findViewById(R.id.search_box);
        String searchTerms = editText.getText().toString();
        Log.d(TAG, "search: " + searchTerms);

        hideKeyboard(this);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.VISIBLE);

        ListView bookListView = (ListView) findViewById(R.id.book_list);

        emptyStateTextView = (TextView) findViewById(R.id.empty);
        bookListView.setEmptyView(emptyStateTextView);

        /*
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("The Old Man and The Sea"));
        if (books.isEmpty()) { emptyStateTextView.setText(R.string.no_books); }
        */

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            adapter = new BookArrayAdapter(this, 0, new ArrayList<Book>());

            //pb = (ProgressBar) findViewById(R.id.progress_bar);
            //pb.setVisibility(ProgressBar.INVISIBLE);
            updateUI();
        } else {
            pb = (ProgressBar) findViewById(R.id.progress_bar);
            pb.setVisibility(ProgressBar.INVISIBLE);
            emptyStateTextView.setText(R.string.no_network);
        }

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return new BookLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

        Log.d(TAG, "onLoadFinished");
        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(ProgressBar.INVISIBLE);

        emptyStateTextView.setText(R.string.no_books);

        adapter.clear();
        adapter.setBooks(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        Log.d(TAG, "onLoaderReset");
        adapter.setBooks(new ArrayList<Book>());
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUI() {
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.book_list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
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

}
