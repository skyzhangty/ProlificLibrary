package com.raymondtz65.prolificlibrary.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class BookListActivity extends ActionBarActivity {

    private static final String SEED_ACTION = "SEED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
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
            Intent intent = new Intent(this,BackgroundService.class);
            intent.setAction(SEED_ACTION);
            startService(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
