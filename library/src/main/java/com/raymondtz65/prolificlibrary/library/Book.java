package com.raymondtz65.prolificlibrary.library;

import java.util.Date;

/**
 * Created by skyzhangty on 4/2/14.
 */
public class Book {
    long id;
    String author;
    String categories;

    Date lastCheckedOut;
    String lastCheckedOutBy;

    String publisher;
    String title;
    String url;

    public Book(String author,String categories,Date lastCheckedOut,String lastCheckedOutBy,String publisher,String title) {
        this.author = author;
        this.categories = categories;
        this.lastCheckedOut = lastCheckedOut;
        this.lastCheckedOutBy = lastCheckedOutBy;
        this.publisher = publisher;
        this.title = title;

    }

    public String getAuthor() {
        return this.author;
    }
    public String getCategories() {return this.categories;}
    public Date getLastCheckedOut() {return this.lastCheckedOut;}
    public String getLastCheckedOutBy() {return this.lastCheckedOutBy;}
    public String getPublisher() {return this.publisher;}
    public String getTitle() {return this.title;}
    public long getId() {return this.id;}

    public String toString() {
        return "author="+author+" "+"categories="+categories+" "+"lastCheckedOut="+lastCheckedOut+" "+"lastCheckedOutBy="+lastCheckedOutBy+" publisher="+publisher+" title="+title;
    }
}
