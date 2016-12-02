package com.example.ale.keychain;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ale on 12/1/16.
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String string = bundle.getString(DownloadService.FILEPATH);
            int resultCode = bundle.getInt(DownloadService.RESULT);
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context,
                        "Download complete. Download URI: " + string,
                        Toast.LENGTH_LONG).show();
           //     textView.setText("Download done");
            } else {
                Toast.makeText(context, "Download failed",
                        Toast.LENGTH_LONG).show();
             //   textView.setText("Download failed");
            }
        }
    }
    }


