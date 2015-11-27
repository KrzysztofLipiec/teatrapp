package com.mobilki.dokultury.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobilki.dokultury.activities.MainActivity;
import com.mobilki.dokultury.models.Category;
import com.mobilki.dokultury.views.adapters.CategoriesAdapter;

public class ResultsFragment extends BaseFragment {
    public static String CATEGORY_NAME = "category";
    public static String CATEGORY_ICON = "icon";

    String mName;
    int mIcon;

    public ResultsFragment () {}

    public static ResultsFragment newInstance(Category category) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, category.getName());
        args.putInt(CATEGORY_ICON, category.getIcon());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(CATEGORY_NAME);
            mIcon = getArguments().getInt(CATEGORY_ICON);
        }
    }

    @Override
    public void onViewCreated(View v, ListView listView) {
        super.onViewCreated(v, listView);
        showCategoryOnTitle(mIcon, mName);
        CategoriesAdapter adapter = new CategoriesAdapter(this.getContext(), ((MainActivity) getActivity()).categories);
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
    }

    @Override
    public void onSearch(String query) {
        super.onSearch(query);
    }

//    private void showCategories(String miasto){
//        Toast.makeText(this.getContext(), miasto, Toast.LENGTH_LONG).show();
//        // tworzysz nowy fragment i wysyłasz go do activity
//        // CategoriesFragment categories = CategoriesFragment.newInstance(miasto);
//        // loadFragment(categories);
//    }
}