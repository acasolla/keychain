package com.example.ale.keychain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ale.keychain.fragments.CitiesFragment;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Intent i = getIntent();
        String message = i.getStringExtra(CitiesFragment.SELECTED_CITY);
        TextView t = (TextView) findViewById(R.id.city);
        t.setText(message);

      }



}
