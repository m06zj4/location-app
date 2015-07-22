package com.example.yf.app0706_yfproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class bmi extends ActionBarActivity {
    EditText et1, et2;
    TextView tv1;
    double height,weight,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        et1 = (EditText) findViewById(R.id.data1);
        et2 = (EditText) findViewById(R.id.data2);
        tv1 = (TextView) findViewById(R.id.result);
    }

    public void cal(View v) {

        try {

                height = Double.parseDouble(et1.getText().toString()) / 100;

                weight = Double.parseDouble(et2.getText().toString());


            result = weight / (height * height);
            tv1.setText(String.valueOf(result));// ++
        }catch (Exception e){
            tv1.setText("輸入有錯誤");

        }






    }

}
