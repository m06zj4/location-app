package com.example.yf.app0706_yfproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyDijkstra extends ActionBarActivity implements BeaconConsumer {


    private BeaconManager beaconManager;
    TextView out, jsonout,v2;
    String UUID, major, minor, classid, classname, Dist;
    Collection<Beacon> max;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dijkstra);
//        out = (TextView) findViewById(R.id.textView2);
        jsonout = (TextView) findViewById(R.id.textView4);
        v2 =(TextView)findViewById(R.id.textView5);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));     //0215為讀取iBeacon  beac為altBeacon
        beaconManager.bind(this);


    }
    public void btn2(View v){
        // 建立一個權值矩陣
        int[][] L1 = { //測試數據1 無向圖 實對稱矩陣 圖3的權值矩陣
                {0, 2, -1, 1, -1, -1},
                {2, 0, 3, 2, -1, -1},
                {-1, 3, 0, 3, 1, 5},
                {1, 2, 3, 0, 1, -1},
                {-1, -1, 1, 1, 0, 2},
                {-1, -1, 5, -1, 2, 0}};
        int[][] L2 = { //測試數據2 有向圖 非對稱矩陣 圖4的權值矩陣
                {0, 10, -1, 30, 100},
                {-1, 0, 50, -1, -1},
                {-1, -1, 0, -1, 10},
                {-1, -1, 20, 0, 60,},
                {-1, -1, -1, -1, 0}};
//        tv1.setText(String.valueOf(dijkstra(L1, 0, 5))+ "\n"+String.valueOf(dijkstra(L2,0,4)));
        v2.setText(String.valueOf(dijkstra(L1, 0, 5)));
        dijkstra(L2, 0, 4);
//        數字+1則為圖中的數字  圖中則為-1轉換回來
        //節點v0到節點v5的短距離為：4  點1～6
        // 節點v0到節點v4的短距離為：60
    };


    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {


                if (beacons.size() > 0) {
                    UUID = beacons.iterator().next().getId1().toString();
                    major = beacons.iterator().next().getId2().toString();
                    minor = beacons.iterator().next().getId3().toString();
                    Dist = String.valueOf(beacons.iterator().next().getDistance());

                    if (max == null) {
                        max = beacons;
                    } else {
                        if (max.iterator().next().getDistance() > beacons.iterator().next().getDistance()) {
                            max = beacons;
                        }
                    }


                    new LoadingDataAsyncTask().execute();

//                    String RSSI = "RSSI:" + String.valueOf(beacons.iterator().next().getRssi()) + "\n";
//                    String Dist = "Distance:" + beacons.iterator().next().getDistance() + "\n";
//                    String android = "address" + beacons.iterator().next().getBluetoothAddress() + "\n";


//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = UUID + major + minor + RSSI + Dist + android;
//                    handler.sendMessage(msg);
                }

            }

        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }

    }

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//
//            if (msg.what == 1) {
//                String print = (String) msg.obj;
//                out.setText(print);
//                new LoadingDataAsyncTask().execute();
//            }
//        }


    //    };
    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://120.114.138.143/compare.php");

//        Log.w("mydebug1",UUID);
//        Log.w("mydebug2",major);
//        Log.w("mydebug3",minor);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("UUID", UUID));
            nameValuePairs.add(new BasicNameValuePair("Major", major));
            nameValuePairs.add(new BasicNameValuePair("Minor", minor));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
//            Log.w("mydebug", "send"+"/n"+UUID+major+minor);

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
//        Log.w("mydebug", test);
        try {
            JSONArray jsonArray = new JSONArray(test);
            JSONObject jsondata = jsonArray.getJSONObject(0);
            classid = jsondata.getString("Class_id");
            classname = jsondata.getString("Class_name");
//            jsonout.setText(classid + classname);
        } catch (Exception e) {
        }
    }


    class LoadingDataAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... param) {
            // getData();
            postData();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            // showData();
            jsonout.setText(classid + "  " + classname);


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
    public static int dijkstra(int[][] L1, int start, int end) {

        boolean[] isLabel = new boolean[L1[0].length];// 是否標號
        int[] N = new int[L1[0].length];// 所有標號的點的下標集合，以標號的先後順序進行存儲，實際上是一個以數組表示的棧
        //int[] zhb_router =new int[L1[0].length];
        int count = 0;//被標號頂點的順序或數目
        int[] Distance = L1[start].clone();// v0到各點的最短距離的初始值
        int index = start;// 從初始點開始
        int presentShortest = 0;//當前臨時最短距離
        N[count] = index;// 把已經標號的下標存入下標集中
        isLabel[index] = true;
        while (count < L1[0].length) {
            // 第一步：標號v_start,即w[start][0]行中找到距離v_start最近的點
            int min = Integer.MAX_VALUE;
            //找出與v_start距離最短的Distance的下標
            for (int i = 0; i < Distance.length; i++) {
                if (!isLabel[i] && Distance[i] != -1 && i != index) {
                    // 如果到這個點有邊,並且沒有被標號
                    if (Distance[i] < min) {
                        min = Distance[i];
                        index = i;// 記錄下這個下標
                    }
                }
            }
            if (index == end) {//已經找到當前點了，就結束查找
                break;
            }
            isLabel[index] = true;//對點進行標號
            count++;//增加一個標定的點
            N[count] = index;// 把已經標號的下標存入下標集中
            if (L1[N[count - 1]][index] == -1
                    || presentShortest + L1[N[count - 1]][index] > Distance[index]) {
// 如果標定的點和它前一個標定的點之間沒有直接相連，或者這兩個點的路徑大於最短路徑
                presentShortest = Distance[index];
            } else {
                presentShortest += L1[N[count - 1]][index];
// zhb_router[i_count]=index;
            }
// 第二步：將Distance中的距離加入vi
            for (int i = 0; i < Distance.length; i++) {
// 如果vi到那個點有邊，則v0到後面點的距離加
                if (Distance[i] == -1 && L1[index][i] != -1) {// 如果以前不可達，則現在可達了
                    Distance[i] = presentShortest + L1[index][i];
                } else if (L1[index][i] != -1
                        && presentShortest + L1[index][i] < Distance[i]) {
// 如果以前可達，但現在的路徑比以前更短，則更換成更短的路徑
                    Distance[i] = presentShortest + L1[index][i];
                }
            }
        }
//如果全部點都遍曆完，則Distance中存儲的是開始點到各個點的最短路徑
        System.out.println("節點v" + start + "到節點v" + end + "的短距離為：" + (Distance[end] - Distance[start]));

        return Distance[end] - Distance[start];
    }

}




//Project下之libs新增aar檔
//