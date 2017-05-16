package com.example.ale.keychain.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ale.keychain.CityActivity;
import com.example.ale.keychain.R;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ale on 11/27/16.
 */

public class CitiesFragment extends ListFragment  implements AdapterView.OnItemClickListener{
    private final static Logger logger = Logger.getLogger("Activity-CitiesFragment");
    public final static String SELECTED_CITY = "SELECTED_CITY";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.info("onCreateView");
        return inflater.inflate(R.layout.fragment_cities,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        logger.info("CLICK!!!!");
        String[] cities = getResources().getStringArray(R.array.Cities);
   //     Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
   //     Snackbar.make(view, "Selected city: " + cities[position], Snackbar.LENGTH_LONG)
    //            .setAction("Action", null).show();
    //    Intent i = new Intent(this,CitiesFragment.class);
        if ( getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE ){
            View v = getActivity().findViewById(R.id.city_fragment);
            TextView t = (TextView) v.findViewById(R.id.city);
            t.setText(cities[position]);
        }else {
            Intent i = new Intent(getActivity(), CityActivity.class);
            i.putExtra(SELECTED_CITY,cities[position]);
            getActivity().startActivity(i);
        }

    //    v.findViewById(R.id)
    }

}
