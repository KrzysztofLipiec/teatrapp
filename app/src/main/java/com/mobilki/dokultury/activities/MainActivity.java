package com.mobilki.dokultury.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mobilki.dokultury.R;
import com.mobilki.dokultury.fragments.BaseFragment;
import com.mobilki.dokultury.fragments.CitiesFragment;
import com.mobilki.dokultury.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import icepick.Icepick;
import icepick.State;

public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentChangeListener {

    public List<Category> categories;

    @State String city;
    @State Integer category;
    @State String target;
    @State String parking;
    @State String hotel;

    @Bind(R.id.fragment) FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);

        categories = new ArrayList<>();
        categories.add(new Category(R.drawable.hotel, "Hotel"));
        categories.add(new Category(R.drawable.kino, "Kino"));
        categories.add(new Category(R.drawable.muzeum, "Muzeum"));
        categories.add(new Category(R.drawable.parking, "Parking"));
        categories.add(new Category(R.drawable.zoo, "ZOO"));

        CitiesFragment fragment = CitiesFragment.newInstance();
        loadFragment(fragment);
    }

    @Override
    public void loadFragment(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
