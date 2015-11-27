package com.mobilki.dokultury.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobilki.dokultury.models.Category;
import com.mobilki.dokultury.views.adapters.PlacesAdapter;

import java.io.IOException;
import java.util.List;

public class ResultsFragment extends BaseFragment {
    public static String CATEGORY_NAME = "category";
    public static String CATEGORY_ICON = "icon";
    public static String CITY_KEY = "city";

    String mName;
    String mCity;
    int mIcon;

    public ResultsFragment () {}

    public static ResultsFragment newInstance(Category category, String city) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, category.getName());
        args.putString(CITY_KEY, city);
        args.putInt(CATEGORY_ICON, category.getIcon());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(CATEGORY_NAME);
            mCity = getArguments().getString(CITY_KEY);
            mIcon = getArguments().getInt(CATEGORY_ICON);
        }
    }

    @Override
    public void onViewCreated(View v, ListView listView) {
        super.onViewCreated(v, listView);
        showCategoryOnTitle(mIcon, mName);
        showSearchBar(false);
        PlacesAdapter adapter = new PlacesAdapter(this.getContext(), loadResults());
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
    }

    public List<Address> loadResults(){
        Geocoder geocoder = new Geocoder(this.getContext());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(String.format("%s, %s", mCity, mName), 15);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return addressList;
    }
//    private void showCategories(String miasto){
//        Toast.makeText(this.getContext(), miasto, Toast.LENGTH_LONG).show();
//        // tworzysz nowy fragment i wysyłasz go do activity
//        // CategoriesFragment categories = CategoriesFragment.newInstance(miasto);
//        // loadFragment(categories);
//    }
}