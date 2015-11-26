package com.mobilki.dokultury.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mobilki.dokultury.R;

public class CitiesFragment extends BaseFragment {
    String[] lastCities = new String[]{"Warszawa", "Łódź"};

    public CitiesFragment() {
    }

    public static CitiesFragment newInstance() {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View v, ListView listView) {
        super.onViewCreated(v, listView);
        setTitle(getResources().getString(R.string.fragment_cities_title));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.item_simple, R.id.text, lastCities);
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
        showCategories(lastCities[position]);
    }

    @Override
    public void onSearch(String query) {
        super.onSearch(query);
        showCategories(query);
    }

    private void showCategories(String miasto) {
        Toast.makeText(this.getContext(), miasto, Toast.LENGTH_LONG).show();
//         tworzysz nowy fragment i wysyłasz go do activity
        CategoriesFragment categories = CategoriesFragment.newInstance(miasto);
        loadFragment(categories);
    }
}
