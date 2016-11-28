package com.example.ale.keychain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger("Keychain-LoginActivity");
    public static final String LOGIN_USERNAME = "com.example.ale.keychain.LOGIN_USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        logger.info("click login");

        EditText username = (EditText) findViewById(R.id.username);
        String value =  username.getText().toString();

        logger.info("Value username=" + value);
        Intent i = new Intent(this,Main2Activity.class);
        i.putExtra(LOGIN_USERNAME,value);
        startActivity(i);
    }
}
