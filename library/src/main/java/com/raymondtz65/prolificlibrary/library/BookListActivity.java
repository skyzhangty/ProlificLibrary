package com.raymondtz65.prolificlibrary.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class BookListActivity extends ActionBarActivity implements BookListFragment.OnListItemClickedListener {

    private static final String SEED_ACTION = "SEED";
    private static final String BROADCAST_ACTION = "UPDATE_UI";
    private LibraryClient mLibraryClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mLibraryClient = APIClient.getInstance().getClient(getApplicationContext(), LibraryClient.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalBroadcastReceiver,new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocalBroadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddBookActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.action_seed) {
            Intent intent = new Intent(this, BackgroundService.class);
            intent.setAction(SEED_ACTION);
            startService(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private BroadcastReceiver mLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_ACTION)) {
                getBookList();
            }
        }
    };

    private void getBookList() {
        mLibraryClient.getAllBooksAsync(new Callback<BookListResponse>() {
            @Override
            public void success(BookListResponse bookListResponse, Response response) {
                ((BookListFragment)getSupportFragmentManager().findFragmentById(R.id.booklistfragment)).updateUI(bookListResponse.getBooks());
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    @Override
    public void onListItemClicked() {

    }
}
