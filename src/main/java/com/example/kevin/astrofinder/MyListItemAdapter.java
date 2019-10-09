package com.example.kevin.astrofinder;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

// Custom list item class for menu items
public class MyListItemAdapter extends BaseAdapter {

    private List<NasaDataActivity.Asteroid> items;
    private Context context;
    private int numItems = 0;

    public MyListItemAdapter(final List<NasaDataActivity.Asteroid> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }

    public int getCount() {
        return numItems;
    }

    public NasaDataActivity.Asteroid getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current list item
        final NasaDataActivity.Asteroid item = items.get(position);
        // Get the layout for the list item
        final RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        // Set the text label as defined in our list item
        TextView txtLabel = (TextView) itemLayout.findViewById(R.id.txtLabel);
        txtLabel.setText(item.getName());

        return itemLayout;
    }

}
