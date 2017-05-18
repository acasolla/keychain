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

import java.util.Arrays;

public class PasswordDetailActivity extends AppCompatActivity {

    private final static String LOG_TAG  = "Activity-DetailActivity";
    private PasswordDbHelper mDbHelper;
    private SQLiteDatabase db;
    private EditText etName;
    private EditText etUrl;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etNote;
    private int _id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        etName = (EditText) findViewById(R.id.psw_name);
        etUrl = (EditText) findViewById(R.id.psw_url);
        etUsername = (EditText) findViewById(R.id.psw_username);
        etPassword = (EditText) findViewById(R.id.psw_psw);
        etNote = (EditText) findViewById(R.id.psw_note);
        Intent in = getIntent();
        PasswordBean bean = (PasswordBean) in.getSerializableExtra(PasswordListActivity.PASSWORD_SELECTED);
        if ( bean != null ){
            _id = bean.get_id();
            etName.setText(bean.getName());
            etUrl.setText(bean.getUrl());
            etUsername.setText(bean.getUsername());
            etPassword.setText(bean.getPassword());
            etNote.setText(bean.getNote());
        }
        Log.d(LOG_TAG,"bean =" + bean);
        mDbHelper = new PasswordDbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        if ( _id < 0 ){
            MenuItem i = menu.findItem(R.id.action_delete);
            i.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                long result = checkBeanAndSave();
                Log.i("result","result" +result );
                String msg = result > 0 ? "Salvataggio effettuato con successo" : null;
                if ( msg != null ){
                    _id = (int)result;
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this,PasswordListActivity.class);
                    startActivity(i);
                }
                return true;
            case R.id.action_delete:
                removeFromDb(_id);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private long checkBeanAndSave(){
        String username =  etUsername.getText().toString();
        String password =  etPassword.getText().toString();
        String name = etName.getText().toString();
        long ret = 0;
        if ( username == null || username.isEmpty() ){
            etUsername.setError("Username obbligatoria");
            ret = -1;
        }if ( password == null || password.isEmpty() ){
            etPassword.setError("password obbligatoria");
            ret = -1;
        }if ( name == null || name.isEmpty() ){
            etName.setError("name obbligatoria");
            ret = -1;
        }
        if ( ret == -1 ) return ret;
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

    private void removeFromDb(int _id){
        String where = "_id=" + _id ;
        long res = db.delete(PasswordContract.PasswordEntry.TABLE_NAME,where,null);
        if ( res  > 0 ){
            Toast.makeText(this,String.format("Password id %s removed",_id),Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,PasswordListActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this,String.format("ERROR removing password id %s ",_id),Toast.LENGTH_LONG).show();
        }


    }
    private long addToDB(PasswordBean bean){

        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.COLUMN_NAME, bean.getName());
        values.put(PasswordContract.PasswordEntry.COLUMN_USERNAME, bean.getUsername());
        values.put(PasswordContract.PasswordEntry.COLUMN_PASSWORD, bean.getPassword());
        values.put(PasswordContract.PasswordEntry.COLUMN_NOTE, bean.getNote());
        values.put(PasswordContract.PasswordEntry.COLUMN_URL, bean.getUrl());
        if ( _id > 0 ){
            String where = "_id=" + _id ;
            Log.i("updating","params: where=" + where );
            return db.update(PasswordContract.PasswordEntry.TABLE_NAME,values,where,null );
        }else{
            return db.insert(PasswordContract.PasswordEntry.TABLE_NAME, null, values);
        }
    }
}
