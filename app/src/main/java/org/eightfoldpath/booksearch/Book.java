package org.eightfoldpath.booksearch;

/**
 * Created by rick on 2/12/17.
 */

public class Book {

    private String name = null;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
