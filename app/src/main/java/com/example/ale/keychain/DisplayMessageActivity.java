package com.example.ale.keychain;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.logging.Logger;

public class DisplayMessageActivity extends AppCompatActivity {
    private final static Logger logger = Logger.getLogger("Activity-Display");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("onCreate");
        setContentView(R.layout.activity_display_message);

        Intent i = getIntent();
        String message = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
        message += " from xml";
        TextView tv = (TextView) findViewById(R.id.text_message);
        tv.setTextSize(40);
        tv.setText(message);

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
    private final int PICK_CONTACT_REQUEST = 0;

    public void pickContact(View view){
        logger.info("pick contact");
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACT_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        logger.info(String.format("requestCode=%s, resultCode=%s",requestCode,resultCode));
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case PICK_CONTACT_REQUEST:
                    logger.info(data.toString());
                  //  contactPicked(data);
                    break;
            }
        } else {
            logger.severe("error in pick contact");

        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            logger.info("name=" + name );
            logger.info("phoneNo=" + phoneNo );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
