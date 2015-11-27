package com.mobilki.dokultury.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobilki.dokultury.R;
import com.mobilki.dokultury.activities.MainActivity;
import com.mobilki.dokultury.models.Category;
import com.mobilki.dokultury.views.adapters.CategoriesAdapter;

public class CategoriesFragment extends BaseFragment {
    public static String CITY_KEY = "city";
    String city;
    public CategoriesFragment () {}

    public static CategoriesFragment newInstance(String city) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(CITY_KEY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString(CITY_KEY);
        }
    }

    @Override
    public void onViewCreated(View v, ListView listView) {
        super.onViewCreated(v, listView);
        showSearchBar(false);
        setTitle(getResources().getString(R.string.fragment_categories_title) + " " + city);
        CategoriesAdapter adapter = new CategoriesAdapter(this.getContext(), ((MainActivity) getActivity()).categories);
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
        Category category = ((MainActivity) this.getContext()).categories.get(position);
        showPlaces(category);
    }

    private void showPlaces(Category category){
        // tworzysz nowy fragment i wysyłasz go do activity
         ResultsFragment results = ResultsFragment.newInstance(category, city);
         loadFragment(results);
    }
}
