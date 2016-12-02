package com.example.ale.keychain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ale.keychain.R;

/**
 * Created by ale on 12/2/16.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] imageId;
    public GridAdapter(Context c,String[] web,int[] Imageid ) {
        this.mContext = c;
        this.imageId = Imageid;
        this.web = web;
    }//https://material.io/icons/
    @Override
    public int getCount() {
        return web.length;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(imageId[position]);
            imageView.setTag(imageId[position]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}
