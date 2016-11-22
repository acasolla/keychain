package com.example.ale.keychain;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Logger;

public class DisplayMessageActivity extends AppCompatActivity {
    private final static Logger logger = Logger.getLogger("Activity-Display");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
    //gets data from intent
        Intent i = getIntent();
        String message = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView tv = new TextView(this);
        tv.setTextSize(40);
        tv.setText(message);

        //Adds the tv to the layout
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(tv);

        TextView detail = (TextView) findViewById(R.id.detail_title);
        detail.setText(R.string.detail_activity);

    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.info("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.info("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.info("onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logger.info("onBackPressed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.info("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.info("onResume");
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        logger.info("onCreate");
    }
}
