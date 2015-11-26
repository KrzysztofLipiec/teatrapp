package com.mobilki.dokultury.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mobilki.dokultury.R;

public class CategoriesFragment extends BaseFragment {
    public static String CITY_KEY = "city";
    String[] categories =  new String[]{"Teatr", "Kino", "Parking", "ZO O"};
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
        setTitle(getResources().getString(R.string.fragment_categories_title) + city);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.item_simple, R.id.text, categories);
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
