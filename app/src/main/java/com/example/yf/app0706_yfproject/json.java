package com.example.yf.app0706_yfproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class json extends ActionBarActivity {

    TextView user_name, personid, school, department_name;
    String json_name, json_studentid,name,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        user_name = (TextView) findViewById(R.id.user_name);
        personid = (TextView) findViewById(R.id.personid);
        school = (TextView) findViewById(R.id.school);
        department_name = (TextView) findViewById(R.id.department_name);

    }

    public void getData(View view) {
        user_name.setText(name+data);

        new LoadingDataAsyncTask().execute();
    }


    class LoadingDataAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... param) {
            postData();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://120.114.104.148/compare.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));

            HttpResponse response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(response.getEntity());
                json(strResult);
                Log.w("mydebug", strResult);
            }

        } catch (IOException e) {

        }
    }

    private void json(String test) {
        // TODO Auto-generated method stub
        // Log.w("my2", test);
        json_name = null;
        json_studentid = null;
        try {

            JSONObject all_json_data = new JSONObject(test);

            name = all_json_data.getJSONObject("user_data").toString();
            data = all_json_data.getString("user_data");

//            name=new JSONArray(new JSONObject(test).getString("user_data")).getJSONObject(1).getString("user_name");


        } catch (Exception e) {
        }
    }

}
