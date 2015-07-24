package com.example.yf.app0706_yfproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dijkstra extends ActionBarActivity {

    final int V = 31;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dijkstra);

        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sour, opti, dest;
                String out;

                EditText et1 = (EditText) findViewById(R.id.editText1);
                EditText et2 = (EditText) findViewById(R.id.editText2);
                EditText et3 = (EditText) findViewById(R.id.editText3);

                try {
                    sour = Integer.parseInt(et1.getText().toString());
                } catch (Exception e) {
                    sour = 0;
                    e.printStackTrace();
                }

                try {
                    opti = Integer.parseInt(et2.getText().toString());
                } catch (Exception e) {
                    opti = 0;
                    e.printStackTrace();
                }

                try {
                    dest = Integer.parseInt(et3.getText().toString());
                } catch (Exception e) {
                    dest = 0;
                    e.printStackTrace();
                }

                out = calculateShortestPath(sour, dest, opti);

                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(out);

            }
        });
    }

    public String calculateShortestPath(int source, int destination, int option) {
        ShortestPath SP = new ShortestPath(V);

        setMap(SP.graph);

        SP.initialMatrix(false);

        SP.floydWarshell.calculateDistance();
        SP.floydWarshell.output();

        SP.dijkstra.calculateDistance(source);
        SP.dijkstra.output(option, destination);

        return SP.TextOut;
    }

    public void setMap(ShortestPath.vertex[] graph) {

        graph[0].adjacentEdge(1, 4);

        graph[1].adjacentEdge(0, 4);
        graph[1].adjacentEdge(2, 6);
        graph[1].adjacentEdge(5, 1);

        graph[2].adjacentEdge(1, 6);
        graph[2].adjacentEdge(3, 5);
        graph[2].adjacentEdge(6, 5);

        graph[3].adjacentEdge(2, 5);
        graph[3].adjacentEdge(4, 5);

        graph[4].adjacentEdge(3, 5);
        graph[4].adjacentEdge(7, 5);
        graph[4].adjacentEdge(9, 15);

        graph[5].adjacentEdge(1, 1);
        graph[5].adjacentEdge(8, 8);

        graph[6].adjacentEdge(2, 5);

        graph[7].adjacentEdge(4, 5);

        graph[8].adjacentEdge(5, 8);
        graph[8].adjacentEdge(10, 5);

        graph[9].adjacentEdge(4, 15);
        graph[9].adjacentEdge(11, 8);

        graph[10].adjacentEdge(8, 5);
        graph[10].adjacentEdge(12, 10);

        graph[11].adjacentEdge(9, 8);
        graph[11].adjacentEdge(14, 11);

        graph[12].adjacentEdge(10, 10);
        graph[12].adjacentEdge(13, 16);

        graph[13].adjacentEdge(12, 16);
        graph[13].adjacentEdge(14, 11);

        graph[14].adjacentEdge(11, 11);
        graph[14].adjacentEdge(13, 11);
        graph[14].adjacentEdge(20, 11);

        graph[15].adjacentEdge(16, 2);

        graph[16].adjacentEdge(15, 2);
        graph[16].adjacentEdge(17, 2);

        graph[17].adjacentEdge(16, 2);
        graph[17].adjacentEdge(18, 6);
        graph[17].adjacentEdge(21, 7);

        graph[18].adjacentEdge(17, 6);
        graph[18].adjacentEdge(19, 6);

        graph[19].adjacentEdge(18, 6);
        graph[19].adjacentEdge(20, 3);

        graph[20].adjacentEdge(14, 11);
        graph[20].adjacentEdge(19, 3);
        graph[20].adjacentEdge(22, 8);

        graph[21].adjacentEdge(17, 7);
        graph[21].adjacentEdge(23, 8);

        graph[22].adjacentEdge(20, 8);
        graph[22].adjacentEdge(24, 12);

        graph[23].adjacentEdge(21, 8);
        graph[23].adjacentEdge(25, 13);

        graph[24].adjacentEdge(22, 12);
        graph[24].adjacentEdge(26, 3);

        graph[25].adjacentEdge(23, 13);
        graph[25].adjacentEdge(26, 4);

        graph[26].adjacentEdge(24, 3);
        graph[26].adjacentEdge(25, 4);
        graph[26].adjacentEdge(30, 23);

        graph[27].adjacentEdge(28, 2);

        graph[28].adjacentEdge(27, 2);
        graph[28].adjacentEdge(29, 6);

        graph[29].adjacentEdge(28, 6);
        graph[29].adjacentEdge(30, 3);

        graph[30].adjacentEdge(26, 23);
        graph[30].adjacentEdge(29, 3);
    }
}
