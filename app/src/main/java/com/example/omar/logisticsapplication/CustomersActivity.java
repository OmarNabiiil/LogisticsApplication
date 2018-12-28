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
import com.example.omar.logisticsapplication.classes.AddCustomerInterface;
import com.example.omar.logisticsapplication.classes.Customer;
import com.example.omar.logisticsapplication.classes.EditCustomerInterface;
import com.example.omar.logisticsapplication.listsAdapters.CustomersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersActivity extends AppCompatActivity implements CustomersAdapter.ButtonClickListener,
        AddCustomerInterface, EditCustomerInterface {

    private RecyclerView recyclerView;
    private List<Customer> customers_list;
    private CustomersAdapter mAdapter;
    private int edit_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        recyclerView = findViewById(R.id.recycler_view);

        customers_list = new ArrayList<>();
        mAdapter = new CustomersAdapter( customers_list,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllCustomers();
    }

    @Override
    public void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Customer c=customers_list.get(viewHolder.getAdapterPosition());
        removeCustomer(c.getName(), viewHolder.getAdapterPosition());
    }

    @Override
    public void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Customer c=customers_list.get(viewHolder.getAdapterPosition());
        edit_position=viewHolder.getAdapterPosition();
        editCustomer(c);
    }

    public void getAllCustomers(){

        String tag_string_req = "req_register";

        String url = Config.TEST_CUSTOMERS_URL;

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
                        Customer c=new Customer(jObj.get("name").toString(), jObj.get("email").toString(),
                                jObj.get("mobile").toString(), jObj.get("address").toString());
                        customers_list.add(c);

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

    public void addCustomer(){
        AddCustomer fr=new AddCustomer();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Add Customer");
    }

    public void editCustomer(Customer c){
        EditCustomer fr=new EditCustomer();
        fr.setListener(this);
        getIntent().putExtra("customer", c);
        fr.show(getSupportFragmentManager(), "Edit Customer");
    }

    public void removeCustomer(final String name, final int position) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_REMOVECUSTOMER_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.equals("removed")){
                    mAdapter.removeItem(position);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onAddClick(Customer c) {
        mAdapter.addItem(c);
    }

    public void Add(View view) {
        addCustomer();
    }

    @Override
    public void onEditClick(Customer c) {
        mAdapter.editItem(c, edit_position);
    }
}
