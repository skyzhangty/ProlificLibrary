package com.raymondtz65.prolificlibrary.library;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import retrofit.RetrofitError;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>

 * helper methods.
 */
public class BackgroundService extends IntentService {

    private static final String SEED_ACTION = "SEED";
    private static final String BROADCAST_ACTION = "UPDATE_UI";
    private static final String SEED_STATUS = "SEED_STATUS";
    private Intent mLocalBroadcastIntent = new Intent(BROADCAST_ACTION);

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
                try {
                    libraryClient.deleteAll();
                }
                catch (RetrofitError e) {
                    mLocalBroadcastIntent.putExtra(SEED_STATUS,getResources().getString(R.string.fail));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(mLocalBroadcastIntent);
                    return;
                }

                //Read seeds.json
                Gson gson = new Gson();
                Book []seedsBooks = gson.fromJson(readSeedsJsonFile(),Book[].class);

                //Seed database
                for(int i=0;i<seedsBooks.length;i++) {
                    Book book = seedsBooks[i];
                    try {
                        libraryClient.addOneBook(book.getAuthor(), book.getCategories(), book.getLastCheckedOut(), book.getLastCheckedOutBy(), book.getPublisher(), book.getTitle());
                    }
                    catch (RetrofitError e) {
                        mLocalBroadcastIntent.putExtra(SEED_STATUS,getResources().getString(R.string.fail));
                        LocalBroadcastManager.getInstance(this).sendBroadcast(mLocalBroadcastIntent);
                        return;
                    }
                }

                //Done seeding and notify the activity
                mLocalBroadcastIntent.putExtra(SEED_STATUS,getResources().getString(R.string.success));
                LocalBroadcastManager.getInstance(this).sendBroadcast(mLocalBroadcastIntent);
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
