package com.mobilki.dokultury.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilki.dokultury.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Bind(R.id.title_bar) TextView mTitle;
    @Bind(R.id.search_bar) EditText mSerachBar;
    @Bind(R.id.listView) ListView mListView;

    OnFragmentChangeListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        ButterKnife.bind(this, view);

        mSerachBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(BaseFragment.this.getContext(), "Szukam " + v.getText().toString(), Toast.LENGTH_SHORT).show();
                    onSearch(v.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });

        this.onViewCreated(view, mListView);
        return view;
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void onViewCreated(View v, ListView listView){}
    public void onSearch(String query){}
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id){}

    public void loadFragment(BaseFragment fragment){
        mListener.loadFragment(fragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentChangeListener) {
            mListener = (OnFragmentChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentChangeListener {
        void loadFragment(BaseFragment fragment);
    }
}
