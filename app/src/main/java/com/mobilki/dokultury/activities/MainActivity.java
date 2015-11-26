package com.mobilki.dokultury.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mobilki.dokultury.R;
import com.mobilki.dokultury.fragments.BaseFragment;
import com.mobilki.dokultury.fragments.CitiesFragment;

import butterknife.Bind;
import icepick.Icepick;
import icepick.State;

public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentChangeListener {

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
