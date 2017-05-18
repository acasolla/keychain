package com.example.ale.keychain;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkingActivity extends AppCompatActivity {

    public final static String RESULT_INTENT= "com.example.ale.keychain.RESULT_INTENT";
    private final static String TAG = "NetworkingActivity";
    private final static String REST_URL_USA = "http://services.groupkt.com/state/get/USA/all";
    private final static String REST_URL_IND = "http://services.groupkt.com/state/get/IND/all";
    ListView listView;

    //private long [] patternSW ={0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500};
    private long[] pattern = {500,1000,500,1000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        listView = (ListView) findViewById(R.id.list);
    }


    public void performCall(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new CallRestTask().execute(REST_URL_USA, REST_URL_IND);
        } else {
            Toast.makeText(this, "You are offline", Toast.LENGTH_SHORT).show();
        }

    }

    private class CallRestTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(NetworkingActivity.this);

        protected void onPreExecute() {
            progressDialog.setMessage("Calling ws..");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... urls) {
            try {
                String result = null;
                for (String url : urls) {
                    result = makeServiceCall(url);
                    publishProgress("Parsed url:" + url);
                    Thread.sleep(1000);
                }
                return result;
            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressDialog.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            try {
                JSONObject object = new JSONObject(result);
                Log.d(TAG, "response json=" + object);
                JSONObject restResponse = object.getJSONObject("RestResponse");
                JSONArray arr = restResponse.getJSONArray("result");
                List<String> values = new ArrayList<String>(arr.length());

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject child = (JSONObject) arr.get(i);
                    String name = child.getString("name");
                    values.add("Name : " + name);
                    Log.i("LL", "nam=" + name);
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NetworkingActivity.this,
                        android.R.layout.simple_list_item_1, values);
                listView.setAdapter(adapter);
                publishNotification("Message is ready, entries=" + values.size(),result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }


    }


    public void publishNotification(String msg,String result) {

        Uri ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Network update")
                        .setContentText(msg)
                        .setSound(ring)
                        .setVibrate(pattern)
                        .setAutoCancel(true);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        resultIntent.putExtra(RESULT_INTENT,result);


// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.ˇ
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotificationActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,  PendingIntent.FLAG_ONE_SHOT );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1 , mBuilder.build());
    }
}
