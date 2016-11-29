package com.example.ale.keychain;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("Keychain-MainActivity");
    private SharedPreferences sharedPref = null;
    private SharedPreferences.Editor editor = null;
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_build:
                logger.info("build");
                addToDB();
                return true;

            case R.id.action_logout:
                logger.info("settings");
                editor.putString(LoginActivity.USERNAME_KEY,null);
                editor.commit();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences(LoginActivity.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        mDbHelper = new PasswordDbHelper(this);

        db = mDbHelper.getWritableDatabase();

        Cursor c = mDbHelper.getPasswords(db,"nome1");
        c.moveToFirst();
        String password = c.getString(
                c.getColumnIndexOrThrow(PasswordContract.PasswordEntry.COLUMN_PASSWORD)
        );

        logger.info("PASSWORD FROM DB=" + password);

        String username = sharedPref.getString(LoginActivity.USERNAME_KEY,null);
        if ( username == null ) {
            Intent i = getIntent();
            username = i.getStringExtra(LoginActivity.LOGIN_USERNAME);
            logger.info("username from LoginActivity=" + username);
        }

        TextView center = (TextView) findViewById(R.id.center);
        center.setText(username);


    }

    private void addToDB(){

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.COLUMN_NAME, "nome1");
        values.put(PasswordContract.PasswordEntry.COLUMN_USERNAME, "username1");
        values.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD, "password1");
        values.put(PasswordContract.PasswordEntry.COLUMN_NOTE, "note1");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PasswordContract.PasswordEntry.TABLE_NAME, null, values);

        logger.info("inserted=" + newRowId);
    }
}
