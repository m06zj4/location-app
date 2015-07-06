package com.example.yf.app0706_yfproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class listview extends ActionBarActivity implements View.OnClickListener {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        lv =(ListView)findViewById(R.id.listView);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);              //方法設定列表為單選程式 choise=選擇

        ArrayList<String> smartPhone=new ArrayList<String>();     //設一個ArrayList名為smartPhone
        // /ArrayList ->排列的List裡面的值都是String
        smartPhone.add("Android");
        smartPhone.add("ios");                                  //將括號裡面的值塞進去剛剛的ArrayList裡
        smartPhone.add("Windows Phone");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_checked,smartPhone);
        lv.setAdapter(adapter);
    }


    public void onClick(View arg0){
        Toast.makeText(this, "選取:" + (lv.getCheckedItemPosition() + 1),
                Toast.LENGTH_LONG).show();
    }
}

