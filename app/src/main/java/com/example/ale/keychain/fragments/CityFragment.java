package com.example.ale.keychain.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ale.keychain.R;

import java.util.logging.Logger;

/**
 * Created by ale on 11/27/16.
 */

public class CityFragment extends Fragment implements CitiesFragment.OnSelectedCity{
    private final static Logger logger = Logger.getLogger("Activity-CityFragment");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.info("onCreateView");
        return inflater.inflate(R.layout.fragment_city,container,false);
    }

    @Override
    public void onSelectedCity(String city) {
        logger.info("city=" + city);
    }
}
