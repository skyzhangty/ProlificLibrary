package com.raymondtz65.prolificlibrary.library;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by skyzhangty on 4/3/14.
 */
public class APIClient {
    public static APIClient instance;
    public static final String SERVER_URL = "http://interview-tianyun.herokuapp.com";

    private RestAdapter mRestAdapter=null;
    private String mBaseURL = SERVER_URL;

    private Map<String, BackgroundService.LibraryClient> mClients = new HashMap<String,BackgroundService.LibraryClient>();
    private APIClient() {

    }

    public static APIClient getInstance(){
        if(instance==null) {
            instance = new APIClient();
        }
        return instance;
    }

    public BackgroundService.LibraryClient getClient(Context context, Class<BackgroundService.LibraryClient>clientClass) {
        if(mRestAdapter==null) {

            mRestAdapter = new RestAdapter.Builder().setEndpoint(mBaseURL).build();

        }
        BackgroundService.LibraryClient libraryClient = mClients.get(clientClass.getCanonicalName());

        if(libraryClient!=null) {
            return libraryClient;
        }
        libraryClient = mRestAdapter.create(clientClass);
        mClients.put(clientClass.getCanonicalName(),libraryClient);
        return libraryClient;

    }

}
