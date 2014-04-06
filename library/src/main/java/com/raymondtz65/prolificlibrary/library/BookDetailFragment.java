package com.raymondtz65.prolificlibrary.library;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class BookDetailFragment extends Fragment {

    TextView mTitleTextView = null;
    TextView mAuthorTextView = null;
    TextView mCategoriesTextView = null;
    TextView mPublisherTextView = null;
    TextView mLastCheckedOutTextView = null;
    Button mCheckedOutButton = null;


    public static BookDetailFragment newInstance() {
        BookDetailFragment fragment = new BookDetailFragment();

        return fragment;
    }
    public BookDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        mTitleTextView = (TextView)view.findViewById(R.id.titleTextView);
        mAuthorTextView = (TextView)view.findViewById(R.id.authorTextView);
        mCategoriesTextView = (TextView)view.findViewById(R.id.categoriesTextView);
        mPublisherTextView = (TextView)view.findViewById(R.id.publisherTextView);
        mLastCheckedOutTextView = (TextView)view.findViewById(R.id.lastCheckedOutTextView);
        mCheckedOutButton = (Button)view.findViewById(R.id.checkoutButton);
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void showBookDetail(long bookID) {
        if(!networkConnected()) {
            Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_network),Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        else {
            LibraryClient libraryClient = APIClient.getInstance().getClient(getActivity().getApplicationContext(), LibraryClient.class);
            libraryClient.getOneBookAsync(bookID, new Callback<BookResponse>() {
                @Override
                public void success(BookResponse bookResponse, Response response) {
                    Book book = bookResponse.getBook();
                    if(book!=null) {
                        updateUI(book);
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(getActivity().getApplicationContext(),retrofitError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void updateUI(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String categories = book.getCategories();
        String publisher = book.getPublisher();
        Date lastCheckedOut =  book.getLastCheckedOut();
        String lastCheckedOutBy = book.getLastCheckedOutBy();

        mTitleTextView.setText(title);
        mAuthorTextView.setText(author);
        mCategoriesTextView.setText(getResources().getString(R.string.tags_detail) + ((categories!=null)?categories:""));
        mPublisherTextView.setText(getResources().getString(R.string.publisher_detail)+((publisher!=null)?publisher:""));
        mLastCheckedOutTextView.setText(getResources().getString(R.string.checkedout_detail));

        if(lastCheckedOut!=null && lastCheckedOutBy!=null) {
            String formattedDate = new SimpleDateFormat("MMMM d,yyyy h:m a").format(lastCheckedOut);
            mLastCheckedOutTextView.setText(getResources().getString(R.string.checkedout_detail)+"\n"+lastCheckedOut+" @ "+formattedDate);
        }

    }

    private boolean networkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork!=null && activeNetwork.isConnected());
    }
}
