package com.mobilki.dokultury.views.adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilki.dokultury.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlacesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Address> mAddresses;

    public PlacesAdapter(Context context, List<Address> addresses){
        mAddresses = addresses;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAddresses.size();
    }

    @Override
    public Object getItem(int position) {
        return mAddresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_place, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Address address = mAddresses.get(position);
        holder.placeName.setText(address.getFeatureName());
        holder.placeDetails.setText(String.format("%s, %s, %s %s", address.getCountryName(),
                address.getLocality(), address.getThoroughfare(), address.getSubThoroughfare()));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.place_name) TextView placeName;
        @Bind(R.id.place_details) TextView placeDetails;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
