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
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";

    List<Address> addressList;
    Category category;
    String mCity;
    String mName;
    int mIcon;
    double mLatitude;
    double mLongitude;

    public ResultsFragment () {}

    public static ResultsFragment newInstance(Category category, String city) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(CITY_KEY, city);
        args.putString(CATEGORY_NAME, category.getName());
        args.putInt(CATEGORY_ICON, category.getIcon());
        args.putDouble(LATITUDE, 0.0);
        args.putDouble(LONGITUDE, 0.0);
        fragment.setArguments(args);
        fragment.category = category;
        return fragment;
    }

    public static ResultsFragment newInstanceCarParkFinder(Category category, String city, double latitude, double longitude) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(CITY_KEY, city);
        args.putString(CATEGORY_NAME, category.getName());
        args.putInt(CATEGORY_ICON, category.getIcon());
        args.putDouble(LATITUDE, latitude);
        args.putDouble(LONGITUDE, longitude);
        fragment.setArguments(args);
        fragment.category = category;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(CATEGORY_NAME);
            mCity = getArguments().getString(CITY_KEY);
            mIcon = getArguments().getInt(CATEGORY_ICON);
            mLatitude = getArguments().getDouble(LATITUDE);
            mLongitude = getArguments().getDouble(LONGITUDE);
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
        getActivityHandle().addDestination(addressList.get(position), true);
        showDestinations();
    }

    public List<Address> loadResults(){
        Geocoder geocoder = new Geocoder(this.getContext());

        try {
            if(mLatitude==0.0 || mLongitude==0.0){
                addressList = geocoder.getFromLocationName(String.format("%s, %s", mCity, mName), 15);
            }else{
                addressList = geocoder.getFromLocation(mLatitude, mLongitude, 15);
                mLatitude=0.0;
                mLongitude=0.0;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return addressList;
    }

    private void showDestinations(){
        // tworzysz nowy fragment i wysyłasz go do activity
         DestinationFragment destinationsFragment = DestinationFragment.newInstance(category);
         loadFragment(destinationsFragment);
    }
}