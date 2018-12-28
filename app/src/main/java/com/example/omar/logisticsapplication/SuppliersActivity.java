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
import com.example.omar.logisticsapplication.classes.AddSupplierInterface;
import com.example.omar.logisticsapplication.classes.Supplier;
import com.example.omar.logisticsapplication.classes.EditSupplierInterface;
import com.example.omar.logisticsapplication.listsAdapters.SuppliersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersActivity extends AppCompatActivity implements SuppliersAdapter.ButtonClickListener,
    AddSupplierInterface, EditSupplierInterface {

    private RecyclerView recyclerView;
    private List<Supplier> Suppliers_list;
    private SuppliersAdapter mAdapter;
    private int edit_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        recyclerView = findViewById(R.id.recycler_view);

        Suppliers_list = new ArrayList<>();
        mAdapter = new SuppliersAdapter( Suppliers_list,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllSuppliers();
    }

    @Override
    public void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Supplier c=Suppliers_list.get(viewHolder.getAdapterPosition());
        removeSupplier(c.getName(), viewHolder.getAdapterPosition());
    }

    @Override
    public void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Supplier c=Suppliers_list.get(viewHolder.getAdapterPosition());
        edit_position=viewHolder.getAdapterPosition();
        editSupplier(c);
    }

    public void getAllSuppliers(){

        String tag_string_req = "req_register";

        String url = Config.TEST_SUPPLIERS_URL;

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
                        Supplier c=new Supplier(jObj.get("name").toString(), jObj.get("email").toString(),
                                jObj.get("mobile").toString(), jObj.get("address").toString());
                        Suppliers_list.add(c);

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

    public void addSupplier(){
        AddSupplier fr=new AddSupplier();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Add Supplier");
    }

    public void editSupplier(Supplier c){
        EditSupplier fr=new EditSupplier();
        fr.setListener(this);
        getIntent().putExtra("Supplier", c);
        fr.show(getSupportFragmentManager(), "Edit Supplier");
    }

    public void removeSupplier(final String name, final int position) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_REMOVESUPPLIER_URL;

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
    public void onAddClick(Supplier c) {
        mAdapter.addItem(c);
    }

    public void Add(View view) {
        addSupplier();
    }

    @Override
    public void onEditClick(Supplier c) {
        mAdapter.editItem(c, edit_position);
    }
}
