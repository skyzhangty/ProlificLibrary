package com.raymondtz65.prolificlibrary.library;

import java.util.Date;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by skyzhangty on 4/4/14.
 */
public interface LibraryClient {
    @Headers("Accept: application/json")
    @DELETE("/clean")
    void deleteAllAsync(Callback<BookListResponse> cb);

    @Headers("Accept: application/json")
    @DELETE("/clean")
    BookListResponse deleteAll();

    @Headers("Accept: application/json")
    @GET("/books")
    void getAllBooksAsync(Callback<BookListResponse> cb);

    @Headers("Accept: application/json")
    @GET("/books")
    BookListResponse getAllBooks();


    @FormUrlEncoded
    @POST("/books")
    void addOneBookAsync(@Field("author") String author, @Field("categories") String categories, @Field("lastCheckedOut") Date lastCheckedOut,
                    @Field("lastCheckedOutBy") String lastCheckedOutBy, @Field("publisher") String publisher, @Field("title") String title,
                    Callback<BookResponse> cb);



    @FormUrlEncoded
    @POST("/books")
    BookResponse addOneBook(@Field("author") String author, @Field("categories") String categories, @Field("lastCheckedOut") Date lastCheckedOut,
                         @Field("lastCheckedOutBy") String lastCheckedOutBy, @Field("publisher") String publisher, @Field("title") String title);
}
