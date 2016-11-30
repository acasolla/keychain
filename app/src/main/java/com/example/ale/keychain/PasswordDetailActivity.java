package com.example.ale.keychain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PasswordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        Intent in = getIntent();
        PasswordBean bean = (PasswordBean) in.getSerializableExtra(PasswordListActivity.PASSWORD_SELECTED);

        Log.d("PasswordDetailActivity","bean =" + bean);
    }
}
