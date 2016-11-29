package com.example.ale.keychain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger("Keychain-LoginActivity");
    public static final String LOGIN_USERNAME = "com.example.ale.keychain.LOGIN_USERNAME";
    public static final String USERNAME_KEY = "com.example.ale.keychain.USERNAME_KEY";
    public static final String PASSWORD_KEY = "com.example.ale.keychain.PASSWORD_KEY";
    public static final String PREFERENCE_FILE_KEY = "com.example.ale.keychain.PREFERENCE_FILE_KEY";
    private SharedPreferences sharedPref = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        String storedUsername = sharedPref.getString(USERNAME_KEY,null);
        logger.info("storedUSername=" + storedUsername);
        if ( storedUsername != null ){
            Intent i = new Intent(this,PasswordsActivity.class);
            startActivity(i);
        }
    }

    public void login(View view){
        logger.info("click login");
        EditText etUsername = (EditText) findViewById(R.id.username);
        EditText etPassword = (EditText) findViewById(R.id.password);
        String username =  etUsername.getText().toString();
        String password =  etPassword.getText().toString();
        logger.info(String.format("username=[%s] password=[%s]",username,password));
        if ( username == null || username.isEmpty()){
            logger.info("username is null");
            String error = getString(R.string.username_null);
            etUsername.setError(error);
        }else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(USERNAME_KEY, username);
            editor.putString(PASSWORD_KEY, password);
            editor.commit();

            logger.info("Value username=" + username);
            Intent i = new Intent(this, PasswordsActivity.class);
            i.putExtra(LOGIN_USERNAME,username);
            startActivity(i);
        }

    }
}
