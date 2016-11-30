package com.example.ale.keychain;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.ale.keychain.adapter.PasswordCursorAdapter;
import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

import java.util.logging.Logger;

public class PasswordsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{
    private static final Logger logger = Logger.getLogger("Keychain-PasswordsActivity");
    private SharedPreferences sharedPref = null;
    private SharedPreferences.Editor editor = null;
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;
    private PasswordCursorAdapter mAdapter;
    private ListView lView;
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
        setContentView(R.layout.activity_passwords);
        lView = (ListView) findViewById(R.id.list);
        sharedPref = getSharedPreferences(LoginActivity.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        mDbHelper = new PasswordDbHelper(this);

        db = mDbHelper.getWritableDatabase();

        Cursor c = mDbHelper.getPasswords(db,"nome1");

        String[] fromColumns = {PasswordContract.PasswordEntry.COLUMN_NAME,PasswordContract.PasswordEntry.COLUMN_URL};
        int[] toViews = {R.id.line_name,R.id.line_name}; // The TextView in simple_list_item_1
       mAdapter = new PasswordCursorAdapter(this,R.layout.password_list_item,c,0);
        lView.setAdapter(mAdapter);

    }

    private void addToDB(){

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.COLUMN_NAME, "nome1");
        values.put(PasswordContract.PasswordEntry.COLUMN_USERNAME, "username1");
        values.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD, "password1");
        values.put(PasswordContract.PasswordEntry.COLUMN_NOTE, "note1");
        values.put(PasswordContract.PasswordEntry.COLUMN_URL, "http://google.it");


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PasswordContract.PasswordEntry.TABLE_NAME, null, values);
        lView.destroyDrawingCache();
        lView.setVisibility(ListView.INVISIBLE);
        lView.setVisibility(ListView.VISIBLE);
        ((BaseAdapter) lView.getAdapter()).notifyDataSetChanged();
        logger.info("inserted=" + newRowId);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}