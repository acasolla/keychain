package com.example.ale.keychain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("Keychain-MainActivity");
    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences(LoginActivity.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        String username = sharedPref.getString(LoginActivity.USERNAME_KEY,null);
        if ( username == null ) {
            Intent i = getIntent();
            username = i.getStringExtra(LoginActivity.LOGIN_USERNAME);
            logger.info("username from LoginActivity=" + username);
        }


        



    }
}
