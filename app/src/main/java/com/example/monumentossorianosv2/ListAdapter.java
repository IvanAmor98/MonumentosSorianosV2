package com.example.monumentossorianosv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<MonumentDTO> {

    private int resourceLayout;
    private Context mContext;

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
            TextView name = (TextView) v.findViewById(R.id.monumentName);
            TextView type = (TextView) v.findViewById(R.id.monumentType);
            TextView address = (TextView) v.findViewById(R.id.monumentAddress);

            if (name != null) {
                name.setText(monument.getName());
            }

            if (type != null) {
                type.setText(monument.getType().toString());
            }

            if (address != null) {
                address.setText(monument.getAddress());
            }
        }

        return v;
    }
}