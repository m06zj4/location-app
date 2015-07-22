package com.example.yf.app0706_yfproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1(View v) {
        Intent intent = new Intent(this, listview.class);
        startActivity(intent);
    }

    public void btn2(View v) {
        Intent intent = new Intent(this, json.class);
        startActivity(intent);
    }

    public void btn3(View v) {
        Intent intent = new Intent(this, bmi.class);
        startActivity(intent);
    }

    public void btn4(View v) {
        Intent intent = new Intent(this, iBeaconAccept.class);
        startActivity(intent);
    }

    public void btn5(View v) {
        Intent intent = new Intent(this, MyDijkstra.class);
        startActivity(intent);
    }

    public void btn6(View v) {
        Intent intent = new Intent(this, MyDijksra_2.class);
        startActivity(intent);
    }

    public void btn7(View v) {
        Intent intent = new Intent(this, AsyncTask.class);
        startActivity(intent);
    }
    public void btn8(View v){
        Intent intent =new Intent(this, Dijkstra.class);
        startActivity(intent);
    }
}