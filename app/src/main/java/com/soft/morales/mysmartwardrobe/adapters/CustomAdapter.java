package com.soft.morales.mysmartwardrobe.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soft.morales.mysmartwardrobe.R;
import com.soft.morales.mysmartwardrobe.model.Garment;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Garment> rowItems;

    public CustomAdapter(Context context, List<Garment> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView garment_pic;
        TextView garment_name;
        TextView brand;
        TextView category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null, false);
            holder = new ViewHolder();

            holder.garment_name = (TextView) convertView
                    .findViewById(R.id.garment_name);
            holder.garment_pic = (ImageView) convertView
                    .findViewById(R.id.garment_pic);
            holder.brand = (TextView) convertView.findViewById(R.id.brand);
            holder.category = (TextView) convertView
                    .findViewById(R.id.category);

            Garment row_pos = rowItems.get(position);


            holder.garment_pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.garment_pic.getLayoutParams().width = 200;
            holder.garment_pic.setImageURI(Uri.parse(row_pos.getPhoto()));
            holder.garment_name.setText(row_pos.getName());
            holder.brand.setText(row_pos.getBrand());
            holder.category.setText(row_pos.getCategory());

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}