package com.raymondtz65.prolificlibrary.library;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AddBookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private EditText mBookTitleEditText;
    private EditText mAuthorEditText;
    private EditText mPublisherEditText;
    private EditText mCategoriesEditText;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AddBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


}
