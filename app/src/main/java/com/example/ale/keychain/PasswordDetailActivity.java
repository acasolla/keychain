package com.example.ale.keychain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ale.keychain.bean.PasswordBean;
import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

public class PasswordDetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = "Activity-DetailActivity";
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;
    private EditText etName;
    private EditText etUrl;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.psw_name);
        etUrl = (EditText) findViewById(R.id.psw_url);
        etUsername = (EditText) findViewById(R.id.psw_username);
        etPassword = (EditText) findViewById(R.id.psw_psw);
        etNote = (EditText) findViewById(R.id.psw_note);

        Intent in = getIntent();
        PasswordBean bean = (PasswordBean) in.getSerializableExtra(PasswordListActivity.PASSWORD_SELECTED);

        if ( bean != null ){
            etName.setText(bean.getName());
            etUrl.setText(bean.getUrl());
            etUsername.setText(bean.getUsername());
            etPassword.setText(bean.getPassword());
            etNote.setText(bean.getNote());
        }

        Log.d("PasswordDetailActivity","bean =" + bean);

        mDbHelper = new PasswordDbHelper(this);

        db = mDbHelper.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                long result = checkBeanAndSave();
                String msg = result > 0 ? "Salvataggio effettuato con successo" : null;
                if ( msg != null ){
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private long checkBeanAndSave(){
        String username =  etUsername.getText().toString();
        String password =  etPassword.getText().toString();
        String name = etName.getText().toString();
        if ( username == null || username.isEmpty() ){
            etUsername.setError("Username obbligatoria");
            return -1;
        }
        if ( password == null || password.isEmpty() ){
            etPassword.setError("password obbligatoria");
            return -1;
        }if ( name == null || name.isEmpty() ){
            etName.setError("name obbligatoria");
            return -1;
        }

        PasswordBean bean = createBeanFromView();
        return addToDB(bean);
    }
    private PasswordBean createBeanFromView(){
        PasswordBean bean = new PasswordBean();
        bean.setName(etName.getText().toString());
        bean.setUsername(etUsername.getText().toString());
        bean.setUrl(etUrl.getText().toString());
        bean.setPassword(etPassword.getText().toString());
        bean.setNote(etNote.getText().toString());
        return bean;
    }

    private long addToDB(PasswordBean bean){
        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.COLUMN_NAME, bean.getName());
        values.put(PasswordContract.PasswordEntry.COLUMN_USERNAME, bean.getUsername());
        values.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD, bean.getPassword());
        values.put(PasswordContract.PasswordEntry.COLUMN_NOTE, bean.getNote());
        values.put(PasswordContract.PasswordEntry.COLUMN_URL, bean.getUrl());
        long newRowId = db.insert(PasswordContract.PasswordEntry.TABLE_NAME, null, values);
        return newRowId;

    }




}
