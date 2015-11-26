package com.mobilki.dokultury.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilki.dokultury.R;
import com.mobilki.dokultury.models.Category;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoriesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Category> mCategories;

    public CategoriesAdapter(Context context, List<Category> categories){
        mCategories = categories;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null){
            rowView = mInflater.inflate(R.layout.item_categories, parent);
            ViewHolder holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Category category = mCategories.get(position);
        holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext, category.getIcon()));
        holder.text.setText(category.getName());

        return rowView;
    }

    static class ViewHolder {
        @Bind(R.id.icon) ImageView icon;
        @Bind(R.id.text) TextView text;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
