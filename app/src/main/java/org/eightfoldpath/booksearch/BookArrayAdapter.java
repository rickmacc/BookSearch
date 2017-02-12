package org.eightfoldpath.booksearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by rick on 2/12/17.
 */

public class BookArrayAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Book> books;

    /*
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/DD/yyyy");
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
    private final DecimalFormat magnitudeFormatter = new DecimalFormat("0.0");
    */

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
        /*
        rowView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getDetailURL())));

            }
        });
        */

        TextView nameView = (TextView) rowView.findViewById(R.id.book_name);
        nameView.setText(books.get(position).getName());
        /*
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        magnitudeCircle.setColor(getMagnitudeColor(currentBook.getMagnitude()));

        magnitudeView.setText(magnitudeFormatter.format(books.get(position).getMagnitude()));

        String location = books.get(position).getLocation();
        String[] locationSegments = location.split("of");
        location = locationSegments[0];
        String distance = context.getString(R.string.near);
        if (locationSegments.length > 1) {
            distance = locationSegments[0];
            location = locationSegments[1];
        }
        TextView locationOffsetView = (TextView) rowView.findViewById(R.id.location_offset);
        locationOffsetView.setText(distance.trim());
        TextView locationView = (TextView) rowView.findViewById(R.id.location);
        locationView.setText(location.trim());

        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        dateView.setText(dateFormatter.format(books.get(position).getDate()));
        TextView timeView = (TextView) rowView.findViewById(R.id.time);
        timeView.setText(timeFormatter.format(books.get(position).getDate()));
        */

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
