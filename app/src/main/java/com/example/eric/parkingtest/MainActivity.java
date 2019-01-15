package com.example.eric.parkingtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Parking> listParking ;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listParking  = new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);

        jsonParse(this);
//        RecyclerView mRecyclerView = findViewById(R.id.parkingItem);
//        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,listParking);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
//        mRecyclerView.setAdapter(myAdapter);
    }

    private void jsonParse(final Context mContext){
        String url = "http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000225-002";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            JSONArray jsonArray = result.getJSONArray("records");

                            for (int i=0 ; i<jsonArray.length();i++){
                                JSONObject records = jsonArray.getJSONObject(i);

                                String name = records.getString("NAME");
                                String area = records.getString("AREA");
                                String pServiceTime = records.getString("SERVICETIME");
                                String address = records.getString("ADDRESS");
                                double tw97x = records.getDouble("TW97X");
                                double tw97y = records.getDouble("TW97Y");
                                listParking.add(new Parking(area,name,pServiceTime,address,tw97x,tw97y));

                            }
                            RecyclerView mRecyclerView = findViewById(R.id.parkingItem);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(mContext,listParking);
                            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,1));
                            mRecyclerView.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }


}
