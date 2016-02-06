package com.mobilki.dokultury.fragments;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.mobilki.dokultury.activities.MainActivity;
import com.mobilki.dokultury.models.Category;
import com.mobilki.dokultury.tracker.Tracker;
import com.mobilki.dokultury.fragments.CategoriesFragment;
import com.mobilki.dokultury.views.adapters.PlacesAdapter;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.common.api.GoogleApiClient;

public class DestinationFragment extends BaseFragment{
    public static String CATEGORY_NAME = "category";
    public static String CATEGORY_ICON = "icon";
    public static String CITY_KEY = "city";

    String mName;
    int mIcon;
    LocationManager locationManager;
    LocationListener locationListener;
    Tracker tracker;

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
        tracker = new Tracker(getContext());
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
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?z=12&t=m&q=loc:38.9419+-78.3020");
        Double myLatitude = 51.7477382;
        Double myLongitude = 19.45479;


       // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=loc:<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")"));
      //  startActivity(intent);


        if(tracker.isLocalizationEnabled() && isOnline())
        {
            tracker.setIsNetworkEnabled(true);
            tracker.getLocation();
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", tracker.getLatitude(), tracker.getLongitude(), address.getLatitude(), address.getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);

        }else{

            Toast.makeText(getActivity(), "Brak włączonej lokalizacji lub brak dostępu do sieci",
                    Toast.LENGTH_LONG).show();
        }

       // Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //mapIntent.setPackage("com.google.android.apps.maps");

       // startActivity(mapIntent);
    }

    @Override
    public void onAddDestinationClick() {
        super.onAddDestinationClick();
        loadFragment(CategoriesFragment.newInstance(getActivityHandle().getCity()));
    }

    @Override
    public void onFindCarParkClick() {
        if(isOnline()) {
            super.onFindCarParkClick();
            Category category = ((MainActivity) this.getContext()).categories.get(5);
            String city = ((MainActivity) this.getContext()).getCity();
            Address address = getActivityHandle().getCurrentTarget();
            ResultsFragment results = ResultsFragment.newInstanceCarParkFinder(category, city, address.getLatitude(), address.getLongitude());
            loadFragment(results);
        }else{
            Toast.makeText(getActivity(), "Brak dostępu do sieci",
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

