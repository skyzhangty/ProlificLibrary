package com.raymondtz65.prolificlibrary.library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class AddBookActivity extends ActionBarActivity implements AddBookFragment.AddBookListener{

    private Menu mMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_book, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home || id==R.id.action_done) {
            onExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            onExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onExit() {
        AddBookFragment addBookFragment = (AddBookFragment) getSupportFragmentManager().findFragmentById(R.id.addbookfragment);
        if(addBookFragment.inputsEmpty()) {
            //All inputs are empty. Return immediately
            finish();
        }
        else {
            //The user input something. Pop up the alert dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getResources().getString(R.string.leave_unsaved_changes));
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onStartAdding() {
        enableUI(false);
    }

    @Override
    public void onFinishAdding() {
        enableUI(true);
    }

    private void enableUI(boolean enabled) {
        //Enable/Disable all UI controls

        AddBookFragment addBookFragment = (AddBookFragment) getSupportFragmentManager().findFragmentById(R.id.addbookfragment);
        enableView(addBookFragment.getView(),enabled);

        //Enable/Disable Menu Items
        if(mMenu!=null) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setEnabled(enabled);
            }
        }
    }

    private void enableView(View view, boolean enabled) {
        if(view instanceof ViewGroup) {
            for(int i=0;i<((ViewGroup) view).getChildCount();i++) {
                enableView(((ViewGroup) view).getChildAt(i), enabled);
            }
        }
        else {
            view.setEnabled(enabled);
        }
    }
}
