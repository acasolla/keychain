package com.example.ale.keychain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.logging.Logger;

public class Main2Activity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("Keychain-MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
       String username = i.getStringExtra(LoginActivity.LOGIN_USERNAME);
        logger.info("username from LoginActivity=" + username);

        TextView tv = new TextView(this);
        tv.setText(username);

        ViewGroup vg = (ViewGroup) findViewById(R.id.activity_main2);
        vg.addView(tv);

        



    }
}
