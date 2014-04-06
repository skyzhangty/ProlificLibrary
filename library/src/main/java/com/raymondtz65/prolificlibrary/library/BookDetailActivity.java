package com.raymondtz65.prolificlibrary.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;


public class BookDetailActivity extends ActionBarActivity implements BookDetailFragment.OnBookUpdated{

    private static final String BOOK_ID="BOOK_ID";
    private ShareActionProvider mShareActionProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        BookDetailFragment bookDetailFragment = (BookDetailFragment)getSupportFragmentManager().findFragmentById(R.id.bookdetailfragment);
        if(bookDetailFragment!=null) {
            bookDetailFragment.showBookDetail(intent.getLongExtra(BOOK_ID,0));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.book_detail, menu);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onBookUpdated(Book book) {
        String sharedStr = getResources().getString(R.string.viewingbook)+book.getTitle()+getResources().getString(R.string.by)+book.getAuthor();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,sharedStr);
        mShareActionProvider.setShareIntent(intent);
    }
}
