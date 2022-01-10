package com.example.monumentossorianosv2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<MonumentDTO> {

    private final int resourceLayout;
    private final Context mContext;

    public ListAdapter(Context context, int resource, List<MonumentDTO> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        MonumentDTO monument = getItem(position);

        if (monument != null) {
            TextView name = v.findViewById(R.id.monumentName);
            TextView type = v.findViewById(R.id.monumentType);
            TextView address = v.findViewById(R.id.monumentAddress);
            ImageView image = v.findViewById(R.id.imageView2);

            if (name != null) {
                name.setText(monument.getName());
            }

            if (type != null) {
                type.setText(monument.getType().toString());
            }

            if (address != null) {
                address.setText(monument.getAddress());
            }

            if (image != null) {
                image.setImageBitmap(monument.getImage());
                image.setOnClickListener(v1 -> {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra("monument", monument);
                    mContext.startActivity(intent);
                });
            }
        }

        return v;
    }
}