package com.example.ale.keychain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ale.keychain.bean.PasswordBean;
import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

public class PasswordDetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = "Activity-DetailActivity";
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        Intent in = getIntent();
        PasswordBean bean = (PasswordBean) in.getSerializableExtra(PasswordListActivity.PASSWORD_SELECTED);

        Log.d("PasswordDetailActivity","bean =" + bean);

        mDbHelper = new PasswordDbHelper(this);

        db = mDbHelper.getWritableDatabase();

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
        Log.d(LOG_TAG,"inserted=" + newRowId);

    }
}
