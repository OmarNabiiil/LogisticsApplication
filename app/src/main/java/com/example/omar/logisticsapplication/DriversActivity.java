package com.example.omar.logisticsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddDriverInterface;
import com.example.omar.logisticsapplication.classes.Driver;
import com.example.omar.logisticsapplication.listsAdapters.DriversAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriversActivity extends AppCompatActivity implements AddDriverInterface {
    private RecyclerView recyclerView;
    private List<Driver> drivers_list;
    private DriversAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        recyclerView = findViewById(R.id.recycler_view);

        drivers_list = new ArrayList<>();
        mAdapter = new DriversAdapter( drivers_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllDrivers();
    }

    public void getAllDrivers(){

        String tag_string_req = "req_register";

        String url = Config.TEST_DRIVERS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Driver c=new Driver(jObj.get("name").toString(), jObj.get("mobile").toString(),
                                jObj.get("address").toString(), jObj.get("cost").toString(), jObj.get("status").toString());
                        drivers_list.add(c);
                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

        };
        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void addDriver(){
        AddDriver fr=new AddDriver();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Add Driver");
    }

    @Override
    public void onAddClick(Driver c) {
        mAdapter.addItem(c);
    }

    public void Add(View view) {
        addDriver();
    }

}
