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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


public class BookListActivity extends ActionBarActivity implements BookListFragment.BookListListener {

    private static final String SEED_ACTION = "SEED";
    private static final String BROADCAST_ACTION = "UPDATE_UI";
    private static final String BOOK_ID="BOOK_ID";
    private static final String SEED_STATUS = "SEED_STATUS";

    private Menu mMenu = null;
    private BookListFragment mBookListFragment = null;

    private boolean mUIEnabled = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mBookListFragment = (BookListFragment)getSupportFragmentManager().findFragmentById(R.id.booklistfragment);
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
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.book_list, menu);
        mMenu = menu;
        if(!mUIEnabled) {
            for(int i=0;i<menu.size();i++) {
                menu.getItem(i).setEnabled(false);
            }
        }
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
            enableUI(false);
            ((ProgressBar)mBookListFragment.getActivity().findViewById(R.id.bookListProcessBar)).setVisibility(View.VISIBLE);
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
                String seed_status = intent.getStringExtra(SEED_STATUS);
                if(seed_status.equals(getResources().getString(R.string.success))) {
                    if (mBookListFragment != null) {
                        mBookListFragment.getBookList();
                    }
                }
                else {
                    ((ProgressBar)mBookListFragment.getActivity().findViewById(R.id.bookListProcessBar)).setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.fail),Toast.LENGTH_LONG).show();
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

    public void onStartRefreshingList() {
        enableUI(false);
    }
    public void onFinishRefreshingList() {
        enableUI(true);
    }

    private void enableUI(boolean enabled) {
        //Enable/Disable all UI controls
        mUIEnabled = enabled;
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.booklistactivity);
        for(int i=0;i<linearLayout.getChildCount();i++) {
            View view = linearLayout.getChildAt(i);
            view.setEnabled(enabled);
        }

        //Enable/Disable Menu Items
        if(mMenu!=null) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setEnabled(enabled);
            }
        }
    }
}
