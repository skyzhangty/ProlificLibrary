package com.raymondtz65.prolificlibrary.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddBookFragment extends Fragment {

    private EditText mBookTitleEditText;
    private EditText mAuthorEditText;
    private EditText mPublisherEditText;
    private EditText mCategoriesEditText;
    private Button mSubmitButton;

    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();

        return fragment;
    }
    public AddBookFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        mBookTitleEditText = (EditText)view.findViewById(R.id.booktitleEditText);
        mAuthorEditText = (EditText)view.findViewById(R.id.authorEditText);
        mPublisherEditText = (EditText)view.findViewById(R.id.publisherEditText);
        mCategoriesEditText = (EditText)view.findViewById(R.id.categoriesEditText);
        mSubmitButton = (Button)view.findViewById(R.id.submitBtn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryClient libraryClient = APIClient.getInstance().getClient(getActivity().getApplicationContext(),LibraryClient.class);
                String title = mBookTitleEditText.getText().toString();
                String author = mAuthorEditText.getText().toString();
                String publisher = mPublisherEditText.getText().toString();
                String categories = mCategoriesEditText.getText().toString();

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(publisher) || TextUtils.isEmpty(categories)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage(getResources().getString(R.string.all_required));
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setNeutralButton(getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if(!networkConnected()) {
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_network),Toast.LENGTH_LONG).show();
                }
                else {
                    libraryClient.addOneBookAsync(author, categories, null, null, publisher, title,
                        new Callback<BookResponse>() {
                            @Override
                            public void success(BookResponse bookResponse, Response response) {
                                getActivity().finish();
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                Toast.makeText(getActivity().getApplicationContext(), retrofitError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    );
                }
            }

        });
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


    public boolean inputsEmpty() {
        String bookTitle = mBookTitleEditText.getText().toString();
        String author = mAuthorEditText.getText().toString();
        String publisher = mPublisherEditText.getText().toString();
        String categories = mCategoriesEditText.getText().toString();

        String inputs = bookTitle+author+publisher+categories;

        return TextUtils.isEmpty(inputs);
    }

    private boolean networkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork!=null && activeNetwork.isConnected());
    }

}
