package com.raymondtz65.prolificlibrary.library;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BackgroundService extends IntentService {

    private static final String SEED_ACTION = "SEED";

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(action.equals(SEED_ACTION)) {
                //Read seeds.json
                String seedsJsonStr = readSeedsJsonFile();

                //Convert json string to Book Object array
                Gson gson = new Gson();
                Book [] seedsBooks = gson.fromJson(seedsJsonStr,Book[].class);
                //Log.v("fuck", gson.toJson(seedsBooks[0]));
                //Seed Database
                LibraryClient libraryClient = APIClient.getInstance().getClient(this,LibraryClient.class);
                //1. Delete everything in the database

                //libraryClient.deleteAll();

                //2. Post to the database

                //for(int i=0;i<seedsBooks.length;i++) {
               //     Book book = seedsBooks[i];
                //    libraryClient.addOneBook(book.getAuthor(),book.getCategories(),book.getLastCheckedOut(),book.getLastCheckedOutBy(),book.getPublisher(),book.getTitle());
               // }
                List<Book> bookList = libraryClient.getAllBooks();
                Log.v("fuck", bookList.get(0).getAuthor());

            }
        }
    }

    private String readSeedsJsonFile() {
        String seedsJsonStr = null;

        try {
            InputStream is = getAssets().open("seeds.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            seedsJsonStr = new String(buffer,"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return seedsJsonStr;
    }

    interface LibraryClient {
        @DELETE("/books/7")
        Response deleteAll();

        @FormUrlEncoded
        @POST("/books")
        Response addOneBook(@Field("author") String author, @Field("categories") String categories, @Field("lastCheckedOut") Date lastCheckedOut,
                            @Field("lastCheckedOutBy") String lastCheckedOutBy, @Field("publisher") String publisher, @Field("title") String title);


        @GET("/books")
        List<Book> getAllBooks();
    }

}
