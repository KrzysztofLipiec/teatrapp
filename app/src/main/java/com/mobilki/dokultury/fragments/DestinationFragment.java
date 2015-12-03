package com.mobilki.dokultury.fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobilki.dokultury.activities.MainActivity;
import com.mobilki.dokultury.models.Category;
import com.mobilki.dokultury.views.adapters.PlacesAdapter;

import java.util.List;
import java.util.Locale;

public class DestinationFragment extends BaseFragment {
    public static String CATEGORY_NAME = "category";
    public static String CATEGORY_ICON = "icon";
    public static String CITY_KEY = "city";

    String mName;
    int mIcon;

    public List<Address> destinations;

    public DestinationFragment () {}

    public static DestinationFragment newInstance(Category category) {
        DestinationFragment fragment = new DestinationFragment();
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
        destinations = ((MainActivity) getActivity()).getDestinations();
    }

    @Override
    public void onViewCreated(View v, final ListView listView) {
        super.onViewCreated(v, listView);
        showCategoryOnTitle(mIcon, mName);
        showSearchBar(false);
        showNavigationButtons(true);
        showAddDestinationButton(destinations.size() < 3);

        final PlacesAdapter adapter = new PlacesAdapter(this.getContext(), destinations);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getActivityHandle().removeDestination(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
    }

    @Override
    public void onStartNavigateClick() {
        super.onStartNavigateClick();
        Address address = getActivityHandle().getCurrentTarget();
        String query = String.format(Locale.ENGLISH, "geo:%f,%f?q=parking", address.getLatitude(), address.getLongitude());
        Uri gmmIntentUri = Uri.parse(query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

    @Override
    public void onAddDestinationClick() {
        super.onAddDestinationClick();
        loadFragment(CategoriesFragment.newInstance(getActivityHandle().getCity()));
    }

    @Override
    public void onFindCarParkClick() {
        super.onFindCarParkClick();
        Category category = ((MainActivity) this.getContext()).categories.get(5);
        String city = ((MainActivity) this.getContext()).getCity();
        Address address = getActivityHandle().getCurrentTarget();
        ResultsFragment results = ResultsFragment.newInstanceCarParkFinder(category, city, address.getLatitude(), address.getLongitude());
        loadFragment(results);
    }
}
