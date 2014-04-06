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
import android.view.View;
import android.widget.ProgressBar;


public class BookListActivity extends ActionBarActivity implements BookListFragment.OnListItemClickedListener {

    private static final String SEED_ACTION = "SEED";
    private static final String BROADCAST_ACTION = "UPDATE_UI";
    private static final String BOOK_ID="BOOK_ID";

    private ProgressBar mBookListProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mBookListProgressBar = (ProgressBar)findViewById(R.id.bookListProcessBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalBroadcastReceiver,new IntentFilter(BROADCAST_ACTION));

        BookListFragment bookListFragment = (BookListFragment)getSupportFragmentManager().findFragmentById(R.id.booklistfragment);
        if(bookListFragment!=null) {
            bookListFragment.getBookList();
        }

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
            mBookListProgressBar.setVisibility(View.VISIBLE);
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
                BookListFragment bookListFragment = (BookListFragment)getSupportFragmentManager().findFragmentById(R.id.booklistfragment);
                if(bookListFragment!=null) {
                    bookListFragment.getBookList();
                }
            }
        }
    };



    @Override
    public void onListItemClicked(long bookID) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BOOK_ID,bookID);
        startActivity(intent);
    }


}
