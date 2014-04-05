package com.raymondtz65.prolificlibrary.library;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>

 * helper methods.
 */
public class BackgroundService extends IntentService {

    private static final String SEED_ACTION = "SEED";
    private static final String BROADCAST_ACTION = "UPDATE_UI";
    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(action.equals(SEED_ACTION)) {
                LibraryClient libraryClient = APIClient.getInstance().getClient(getApplicationContext(),LibraryClient.class);

                //Delete everything in the database
                libraryClient.deleteAll();

                //Read seeds.json
                Gson gson = new Gson();
                Book []seedsBooks = gson.fromJson(readSeedsJsonFile(),Book[].class);

                //Seed database
                for(int i=0;i<seedsBooks.length;i++) {
                    Book book = seedsBooks[i];
                    libraryClient.addOneBook(book.getAuthor(),book.getCategories(),book.getLastCheckedOut(),book.getLastCheckedOutBy(),book.getPublisher(),book.getTitle());
                }

                //Done seeding and notify the activity
                Intent localBroadcastIntent = new Intent(BROADCAST_ACTION);
                LocalBroadcastManager.getInstance(this).sendBroadcast(localBroadcastIntent);
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

}
