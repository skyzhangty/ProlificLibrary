package com.raymondtz65.prolificlibrary.library;

/**
 * Created by skyzhangty on 4/2/14.
 */
public class Book {
    String author;
    String categories;
    String lastCheckedOut;
    String lastCheckedOutBy;
    String publisher;
    String title;
    String url;

    public Book(String author,String categories,String lastCheckedOut,String lastCheckedOutBy,String publisher,String title,String url) {
        this.author = author;
        this.categories = categories;
        this.lastCheckedOut = lastCheckedOut;
        this.lastCheckedOutBy = lastCheckedOutBy;
        this.publisher = publisher;
        this.title = title;
        this.url = url;
    }

}
