package com.example.omar.logisticsapplication;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddTransportationInterface;
import com.example.omar.logisticsapplication.classes.Driver;
import com.example.omar.logisticsapplication.classes.SupplierOrder;
import com.example.omar.logisticsapplication.classes.Transportation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSupplierTransportation extends DialogFragment {

    public static final String TAG=AddSupplier.class.getSimpleName();
    private AddTransportationInterface mListener;

    private Spinner drivers;
    private Spinner orders;
    private ArrayList<Driver> driversObject = new ArrayList<>();
    private ArrayList<SupplierOrder> ordersObject = new ArrayList<>();
    private Button addTransportation;
    private Button cancel;

    public AddSupplierTransportation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_transportation, container, false);

        orders=view.findViewById(R.id.list_orders);
        drivers=view.findViewById(R.id.list_drivers);
        addTransportation=view.findViewById(R.id.add_transportation);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        addTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierOrder selectedOrder = ordersObject.get(orders.getSelectedItemPosition());
                Driver selectedDriver = driversObject.get(drivers.getSelectedItemPosition());
                addTransportationDB(""+selectedOrder.getId(), selectedDriver,""+getCurrentDate());
            }
        });

        getAllDrivers();
        getAllSuppliersOrders();

        return view;
    }

    public void setListener(AddTransportationInterface listener) {
        mListener = listener;
    }

    private void addTransportationDB(final String orderNo, final Driver driver,final String startTime) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_ADDSUPPLIERORDERTRANSPOTATION_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //check for success first
                Transportation t=new Transportation(driver, orderNo);
                t.setStartTime(startTime);
                t.setOrderFrom("Supplier");
                mListener.onAddClick(t);
                dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("order_number", orderNo);
                params.put("driver_name", driver.getName());
                params.put("driver_mobile", driver.getMobile());
                params.put("start_time", startTime);
                params.put("status", "0");

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getAllDrivers(){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_AVAILABLEDRIVERS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    Log.d("loginres",a.toString());
                    ArrayList<String> myDrivers=new ArrayList<String>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Driver c=new Driver(jObj.get("name").toString(),
                                jObj.get("mobile").toString(), jObj.get("address").toString(), jObj.get("cost").toString(), jObj.get("status").toString());
                        driversObject.add(c);
                        myDrivers.add(jObj.get("name").toString());

                    }

                    ArrayAdapter<String> drivers_adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, myDrivers);
                    drivers.setAdapter(drivers_adapter);
                    drivers_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    public void getAllSuppliersOrders(){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_SUPPLIERSORDERSFORTRANSPOTATION_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    ArrayList<String> myOrders=new ArrayList<String>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        SupplierOrder c=new SupplierOrder(jObj.get("supplier name").toString(), jObj.get("mobile").toString(),
                                jObj.get("address").toString(), jObj.get("product name").toString(), jObj.get("quantity").toString(), "", jObj.get("transportation").toString());
                        c.setId(jObj.get("id").toString());
                        ordersObject.add(c);
                        myOrders.add(jObj.get("id").toString());

                    }

                    ArrayAdapter<String> orders_adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, myOrders);
                    orders.setAdapter(orders_adapter);
                    orders_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


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

    public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        String formattedDate = df.format(c);

        return formattedDate;

    }
}
