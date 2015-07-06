package com.example.yf.app0706_yfproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class AsyncTask extends ActionBarActivity {
    TextView out;
    int yf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        out=(TextView)findViewById(R.id.textView);
        new LoadingDataAsyncTask().execute();
    }


    class LoadingDataAsyncTask extends android.os.AsyncTask<String, Integer, Integer> {

        ProgressDialog dialog;
        @Override
        protected Integer doInBackground(String... param) {

            for (yf=0;yf<100;yf++)
            {
                try {
                    Thread.sleep(125);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(yf);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(AsyncTask.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(yf);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);
            out.setText(String.valueOf(yf));
        }
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            dialog.dismiss();
            out.setText("finish");


        }



    }

}

