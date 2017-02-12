package org.eightfoldpath.booksearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rick on 2/12/17.
 */

public class BookArrayAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Book> books;

    public BookArrayAdapter(Context context, int resource, ArrayList<Book> books) {
        super(context, resource, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        final Book currentBook = books.get(position);

        // Set a click listener to open a detail page in a browser
        rowView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getInfoUrl())));

            }
        });

        TextView nameView = (TextView) rowView.findViewById(R.id.book_name);
        nameView.setText(books.get(position).getName());
        TextView authorView = (TextView) rowView.findViewById(R.id.author);
        authorView.setText(books.get(position).getAuthor());

        return rowView;
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> data) {
        books.addAll(data);
        notifyDataSetChanged();
    }

}
