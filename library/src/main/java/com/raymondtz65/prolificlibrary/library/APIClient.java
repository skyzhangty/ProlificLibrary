package com.raymondtz65.prolificlibrary.library;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by skyzhangty on 4/3/14.
 */
public class APIClient {
    public static APIClient instance;
    public static final String SERVER_URL = "http://interview-tianyun.herokuapp.com";

    private RestAdapter mRestAdapter=null;
    private String mBaseURL = SERVER_URL;

    private Map<String, LibraryClient> mClients = new HashMap<String,LibraryClient>();
    private APIClient() {

    }

    public static APIClient getInstance(){
        if(instance==null) {
            instance = new APIClient();
        }
        return instance;
    }

    public LibraryClient getClient(Context context, Class<LibraryClient>clientClass) {
        if(mRestAdapter==null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss zzz").create();
            mRestAdapter = new RestAdapter.Builder().setEndpoint(mBaseURL).setConverter(new GsonConverter(gson)).build();

        }
        LibraryClient libraryClient = mClients.get(clientClass.getCanonicalName());

        if(libraryClient!=null) {
            return libraryClient;
        }
        libraryClient = mRestAdapter.create(clientClass);
        mClients.put(clientClass.getCanonicalName(),libraryClient);
        return libraryClient;

    }

}
