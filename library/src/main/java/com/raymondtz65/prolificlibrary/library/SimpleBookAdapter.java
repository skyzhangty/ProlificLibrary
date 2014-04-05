package com.raymondtz65.prolificlibrary.library;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyzhangty on 4/5/14.
 */
public class SimpleBookAdapter extends BaseAdapter {
    private Context mContext;
    private List<Book> mBookList = new ArrayList<Book>();
    private LayoutInflater mInflater=null;
    public SimpleBookAdapter(Context context, List<Book> bookList) {
        mContext = context;
        mBookList = bookList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(mBookList==null) return 0;
        Log.v("fuck", mBookList.size()+"");
        return mBookList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_2,viewGroup, false);


        }
        TextView titleTextView = (TextView) convertView.findViewById(android.R.id.text1);
        TextView authorTextView = (TextView) convertView.findViewById(android.R.id.text2);

        titleTextView.setText( ((Book)getItem(position)).getTitle() );
        authorTextView.setText( ((Book)getItem(position)).getAuthor() );
        titleTextView.setTextColor(Color.BLACK);
        authorTextView.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }
}
