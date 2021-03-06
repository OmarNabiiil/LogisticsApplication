package com.example.omar.logisticsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddSupplierOrderInterface;
import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.classes.Supplier;
import com.example.omar.logisticsapplication.classes.SupplierOrder;
import com.example.omar.logisticsapplication.listsAdapters.ProductsAdapter;
import com.example.omar.logisticsapplication.listsAdapters.SuppliersOrdersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuppliersOrdersActivity extends AppCompatActivity implements AddSupplierOrderInterface {

    private RecyclerView recyclerView;
    private List<SupplierOrder> orders_list;
    private SuppliersOrdersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_orders);

        recyclerView = findViewById(R.id.recycler_view);

        orders_list = new ArrayList<>();
        mAdapter = new SuppliersOrdersAdapter( orders_list );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllOrders();
    }

    public void getAllOrders(){

        String tag_string_req = "req_register";

        String url = Config.TEST_SUPPLIERSORDERS_URL;

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

                        SupplierOrder c=new SupplierOrder(jObj.get("supplier name").toString(), jObj.get("mobile").toString(),
                                jObj.get("address").toString(), jObj.get("product name").toString(), jObj.get("quantity").toString(), "", jObj.get("transportation").toString());
                        c.setId(jObj.get("id").toString());
                        c.setDate(jObj.get("created at").toString());
                        orders_list.add(c);
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
    public void onAddClick(SupplierOrder s) {
        mAdapter.addItem(s);
    }

    public void Add(View view) {
        AddSupplierOrderFragment fr=new AddSupplierOrderFragment();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Add Order");
    }
}
