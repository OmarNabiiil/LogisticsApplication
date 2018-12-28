package com.example.omar.logisticsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddTransportationInterface;
import com.example.omar.logisticsapplication.classes.ArrivedTransportationInterface;
import com.example.omar.logisticsapplication.classes.Driver;
import com.example.omar.logisticsapplication.classes.Supplier;
import com.example.omar.logisticsapplication.classes.Transportation;
import com.example.omar.logisticsapplication.listsAdapters.TransportationsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuppliersTransportationActivity extends AppCompatActivity implements AddTransportationInterface,
        TransportationsAdapter.ArrivedClickListener, ArrivedTransportationInterface {

    private RecyclerView recyclerView;
    private List<Transportation> transportations_list;
    private TransportationsAdapter mAdapter;
    private int arrived_adapter_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_transportation);

        recyclerView = findViewById(R.id.recycler_view);

        transportations_list = new ArrayList<>();
        mAdapter = new TransportationsAdapter( transportations_list,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllTransportations();
    }

    public void getAllTransportations(){

        String tag_string_req = "req_register";

        String url = Config.TEST_SUPPLIERSORDERSTRANSPOTATION_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    Log.d("loginres",a.toString());
                    ArrayList<JSONObject> myClients=new ArrayList<JSONObject>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Driver d= new Driver(jObj.get("name").toString(), jObj.get("mobile").toString(),
                                jObj.get("address").toString(), jObj.get("cost").toString(),
                                jObj.get("status").toString());
                        Transportation t=new Transportation(d, jObj.get("order_number").toString());
                        t.setStartTime(jObj.get("start_time").toString());
                        transportations_list.add(t);

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

    @Override
    public void onAddClick(Transportation t) {
        mAdapter.addItem(t);
    }

    @Override
    public void onArrivedClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        arrived_adapter_position = viewHolder.getAdapterPosition();
        Transportation t = transportations_list.get(viewHolder.getAdapterPosition());
        Bundle b = new Bundle();
        b.putSerializable("transportation", t);
        ArrivedTransportationFragment fr = new ArrivedTransportationFragment();
        fr.setArguments(b);
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Transportation");
    }

    public void Add(View view) {
        AddSupplierTransportation fr = new AddSupplierTransportation();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Suppliers Transportation");
    }

    @Override
    public void onArrive() {
        mAdapter.removeItem(arrived_adapter_position);
    }
}
