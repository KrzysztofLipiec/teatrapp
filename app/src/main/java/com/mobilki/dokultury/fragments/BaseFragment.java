package com.mobilki.dokultury.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilki.dokultury.R;
import com.mobilki.dokultury.activities.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Bind(R.id.title_bar) TextView mTitle;
    @Bind(R.id.search_bar) EditText mSerachBar;
    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.title_holder) FrameLayout mTitleHolder;
    @Bind(R.id.icon) ImageView mIcon;
    @Bind(R.id.text) TextView mText;
    @Bind(R.id.navigation_buttons) RelativeLayout mNavigationButtons;
    @Bind(R.id.add_destination) Button mAddDestinationButton;
    @Bind(R.id.start_navigate) Button mStartNavigateButton;

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

        mAddDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddDestinationClick();
            }
        });

        mStartNavigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartNavigateClick();
            }
        });

        this.onViewCreated(view, mListView);
        return view;
    }

    public void setTitle(String title){
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
        mTitleHolder.setVisibility(View.GONE);
    }

    public void showCategoryOnTitle(int icon, String name){
        mTitle.setVisibility(View.GONE);
        mIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
        mText.setText(name);
        mTitleHolder.setVisibility(View.VISIBLE);
    }

    public void showSearchBar(boolean check){
        int visible = check ? View.VISIBLE : View.GONE;
        mSerachBar.setVisibility(visible);
    }

    public void showNavigationButtons(boolean check){
        mNavigationButtons.setVisibility(check ? View.VISIBLE : View.GONE);
    }

    public void showAddDestinationButton(boolean check){
        mAddDestinationButton.setVisibility(check ? View.VISIBLE : View.GONE);
    }

    public void onViewCreated(View v, ListView listView){}
    public void onSearch(String query){}
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id){}
    public void onAddDestinationClick(){}
    public void onStartNavigateClick(){}

    public void loadFragment(BaseFragment fragment){
        mListener.loadFragment(fragment);
    }

    public MainActivity getActivityHandle(){
        return (MainActivity) getActivity();
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
