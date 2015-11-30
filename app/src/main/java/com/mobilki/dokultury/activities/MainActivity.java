package com.mobilki.dokultury.activities;

import android.location.Address;
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
    @State int target;

    public List<Address> destinations = new ArrayList<>();

    @Bind(R.id.fragment) FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);

        categories = new ArrayList<>();
        categories.add(new Category(R.drawable.teatr, "Teatr"));
        categories.add(new Category(R.drawable.muzeum, "Muzeum"));
        categories.add(new Category(R.drawable.zoo, "ZOO"));
        categories.add(new Category(R.drawable.kino, "Kino"));
        categories.add(new Category(R.drawable.hotel, "Hotel"));
        categories.add(new Category(R.drawable.parking, "Parking"));

        CitiesFragment fragment = CitiesFragment.newInstance();
        loadFragment(fragment);
    }

    @Override
    public void loadFragment(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.pop_in, R.anim.slide_down);
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    public boolean addDestination(Address address, Boolean before){
        if (destinations.size() >= 2)
            return false;

        if (before && destinations.size() == 1){
            Address tmp = destinations.set(0, address);
            destinations.add(tmp);
        } else {
            destinations.add(address);
        }

        return true;
    }

    public Address setCurrentTarget(int id){
        if (id >= destinations.size()){
            return null;
        }

        target = id;
        return destinations.get(id);
    }

    public Address getCurrentTarget(){
        if (destinations.size() > 0) {
            return destinations.get(target);
        } else {
            return null;
        }
    }

    public List<Address> getDestinations(){
        if (destinations == null){
            destinations = new ArrayList<>();
        }

        return destinations;
    }

    public Address removeDestination(int position){
        return destinations.remove(position);
    }

    public String getCity() {
        return city;
    }

    public MainActivity setCity(String city) {
        this.city = city;
        return this;
    }

    public Category getCategory() {
        return categories.get(category);
    }

    public MainActivity setCategory(Category category) {
        this.category = categories.indexOf(category);
        return this;
    }
}
