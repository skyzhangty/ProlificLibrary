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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class BookListFragment extends Fragment {


    private OnListItemClickedListener mListener;

    private List<Book> mBookList = new ArrayList<Book>();
    private ListView mListView = null;
    private ProgressBar mProgressBar = null;
    private TextView mEmptyListTextView = null;
    private SimpleBookAdapter mAdapter = null;


    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        return fragment;
    }
    public BookListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        mProgressBar = (ProgressBar)view.findViewById(R.id.bookListProcessBar);
        mEmptyListTextView = (TextView)view.findViewById(R.id.listemptyTextView);
        mListView = (ListView)view.findViewById(R.id.bookListView);
        mAdapter = new SimpleBookAdapter(getActivity().getApplicationContext(),mBookList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                long bookID = mBookList.get(position).getId();
                mListener.onListItemClicked(bookID);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBookList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnListItemClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getBookList() {
        mProgressBar.setVisibility(View.VISIBLE);
        if(!networkConnected()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
        else {
            LibraryClient libraryClient = APIClient.getInstance().getClient(getActivity().getApplicationContext(),LibraryClient.class);
            libraryClient.getAllBooksAsync(new Callback<BookListResponse>() {
                @Override
                public void success(BookListResponse bookListResponse, Response response) {
                    updateUI(bookListResponse.getBooks());
                    mProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(getActivity().getApplicationContext(),retrofitError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void updateUI(List<Book> bookList) {
        mBookList = bookList;
        mAdapter.setList(mBookList);
        mAdapter.notifyDataSetChanged();

        if(bookList.size()==0) {
            mEmptyListTextView.setVisibility(View.VISIBLE);
        }
        else {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean networkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork!=null && activeNetwork.isConnected());
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListItemClickedListener {
        // TODO: Update argument type and name
        public void onListItemClicked(long bookID);
    }

}
