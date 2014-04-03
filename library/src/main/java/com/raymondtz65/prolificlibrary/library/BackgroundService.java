package com.raymondtz65.prolificlibrary.library;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import retrofit.http.DELETE;

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
                Book [] seedBooks = gson.fromJson(seedsJsonStr,Book[].class);
                //Seed Database
                //1. Delete everything in the database
                LibraryClient libraryClient = APIClient.getInstance().getClient(this,LibraryClient.class);
                libraryClient.deleteAll();
                //2. Post to the database
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
        @DELETE("/clean")
        void deleteAll();

    }

}
