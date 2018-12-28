package com.example.omar.logisticsapplication;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddDriverInterface;
import com.example.omar.logisticsapplication.classes.Driver;

import java.util.HashMap;
import java.util.Map;

public class AddDriver extends DialogFragment {

    public static final String TAG=AddDriver.class.getSimpleName();
    private AddDriverInterface mListener;

    private EditText driver_name;
    private EditText driver_mobile;
    private EditText driver_address;
    private EditText driver_cost;
    private Button add_driver;
    private Button cancel;

    public AddDriver() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_driver, container, false);

        driver_name=view.findViewById(R.id.txt_driver_name);
        driver_mobile=view.findViewById(R.id.txt_driver_mobile);
        driver_address =view.findViewById(R.id.txt_driver_address);
        driver_cost =view.findViewById(R.id.txt_driver_cost);
        add_driver=view.findViewById(R.id.add_driver);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserDB();
            }
        });

        return view;
    }

    public void setListener(AddDriverInterface listener) {
        mListener = listener;
    }

    public void addUserDB(){
        String name = driver_name.getText().toString().trim();
        String mobile = driver_mobile.getText().toString().trim();
        String address = driver_address.getText().toString().trim();
        String cost = driver_cost.getText().toString().trim();

        if (!name.isEmpty() && !mobile.isEmpty() && !address.isEmpty() && !cost.isEmpty()) {
            addDriver(name, mobile, address, cost);
        } else {
            Toast.makeText(getContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void addDriver(final String name, final String mobile, final String address, final String cost) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_ADDDRIVER_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //check for success first
                Driver d= new Driver(name, mobile, address, cost, "0");
                mListener.onAddClick(d);
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
                params.put("name", name);
                params.put("mobile", mobile+"");
                params.put("address", address);
                params.put("cost", cost);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
