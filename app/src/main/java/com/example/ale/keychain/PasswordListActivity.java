package com.example.ale.keychain;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.ale.keychain.adapter.PasswordCursorAdapter;
import com.example.ale.keychain.bean.PasswordBean;
import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

import java.util.logging.Logger;

public class PasswordListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final Logger logger = Logger.getLogger("Keychain-PasswordListActivity");
    public static final String PASSWORD_SELECTED = "com.example.ale.keychain.PASSWORD_SELECTED";
    private SharedPreferences sharedPref = null;
    private SharedPreferences.Editor editor = null;
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;
    private PasswordCursorAdapter mAdapter;
    private ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_passwords);
        sharedPref = getSharedPreferences(LoginActivity.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        lView = (ListView) findViewById(R.id.list);
        mDbHelper = new PasswordDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        lView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor c = mDbHelper.getPasswords(db,null);
        mAdapter = new PasswordCursorAdapter(this,R.layout.password_list_item,c,0);
        lView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                logger.info("build");
                startDetail(null);
                return true;
            case R.id.action_logout:
                logger.info("settings");
                editor.putString(LoginActivity.USERNAME_KEY,null);
                editor.commit();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Cursor c = (Cursor) mAdapter.getItem(position);
        PasswordBean bean = new PasswordBean(c);
        logger.info("object selected = " + bean);
       startDetail(bean);
    }

    private void startDetail(PasswordBean bean){
        Intent intent = new Intent(this,PasswordDetailActivity.class);
        intent.putExtra(PASSWORD_SELECTED,bean);
        startActivity(intent);
    }


}

//https://material.io/icons/
