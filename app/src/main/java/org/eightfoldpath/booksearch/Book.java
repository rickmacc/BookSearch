package org.eightfoldpath.booksearch;

/**
 * Created by rick on 2/12/17.
 */

public class Book {

    private String name = null;
    private String infoUrl = null;
    private String author = null;

    public Book(String name, String infoUrl, String author) {
        this.name = name;
        this.infoUrl = infoUrl;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
