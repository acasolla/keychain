package com.example.ale.keychain;

import android.app.ListActivity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ale.keychain.adapter.GridAdapter;
import com.example.ale.keychain.sql.PasswordContract;
import com.example.ale.keychain.sql.PasswordDbHelper;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("Keychain-MainActivity");



    private GridView gridView;
    static final String[] names = new String[] {"Camera", "Download", "Keychain", "Networking"};
    static final int[] images = new int[]{R.mipmap.ic_photo_camera_black_48dp,
                                          R.mipmap.ic_file_download_black_48dp,
                                          R.mipmap.ic_vpn_key_black_48dp,
                                          R.mipmap.ic_settings_ethernet_black_48dp};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(MainActivity.this, names, images);
        gridView.setAdapter(adapter);

    }

    public void clickIcon(View view){
        Intent i = null;
        final int id = (int) view.getTag();
        switch (id){
            case R.mipmap.ic_photo_camera_black_48dp:
                Log.d("TAG","Clicked camera");
                i = new Intent(this,CameraActivity.class);
                break;
            case R.mipmap.ic_file_download_black_48dp:
                Log.d("TAG","Clicked camera");
                i = new Intent(this,DownloadActivity.class);
                break;
            case R.mipmap.ic_settings_ethernet_black_48dp:
                Log.d("TAG","Clicked camera");
                i = new Intent(this,NetworkingActivity.class);
                break;
            case R.mipmap.ic_vpn_key_black_48dp:
                Log.d("TAG","Clicked camera");
                i = new Intent(this,LoginActivity.class);
                break;


        }

        startActivity(i);
    }

}
