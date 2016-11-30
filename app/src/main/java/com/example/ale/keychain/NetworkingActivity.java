package com.example.ale.keychain;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkingActivity extends AppCompatActivity {
    private final static String TAG = "NetworkingActivity";
    private final static String REST_URL = "http://services.groupkt.com/state/get/USA/all";
    TextView textView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        textView = (TextView) findViewById(R.id.tv);
        listView = (ListView) findViewById(R.id.list);
    }




    public void performCall( View view ){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new CallRestTask().execute(REST_URL);
        } else {
            Toast.makeText(this,"You are offline",Toast.LENGTH_SHORT).show();
        }
    }
    private class CallRestTask extends AsyncTask<String,Void,String> {
        private ProgressDialog progressDialog = new ProgressDialog(NetworkingActivity.this);
        protected void onPreExecute() {
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                return makeServiceCall(params[0]);
            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
           // textView.setText(result);
            try {
                JSONObject object = new JSONObject(result);
                Log.d(TAG,"response json=" + object);
                JSONObject restResponse = object.getJSONObject("RestResponse");
                JSONArray arr = restResponse.getJSONArray("result");
                List<String> values = new ArrayList<String>(arr.length());

                for ( int i = 0; i < arr.length(); i++ ){
                    JSONObject child = (JSONObject) arr.get(i);
                    String name = child.getString("name");
                    values.add("Name : " + name);
                    Log.i("LL", "nam=" + name);
                }
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NetworkingActivity.this,
                        android.R.layout.simple_list_item_1, values);
                listView.setAdapter(adapter);
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
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
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
}