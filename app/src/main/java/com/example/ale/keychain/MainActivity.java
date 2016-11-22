package com.example.ale.keychain;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private final static Logger logger = Logger.getLogger("Activity-Main");

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendMessage(View view ){
        Intent i = new Intent(this,DisplayMessageActivity.class);
        EditText et = (EditText) findViewById(R.id.edit_message);
        String message = et.getText().toString();
        logger.info("message=" + et.getText());
        i.putExtra(EXTRA_MESSAGE,message);
        startActivity(i);
        logger.info("starting activity");
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
