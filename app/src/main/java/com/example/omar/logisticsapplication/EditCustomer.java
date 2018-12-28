package com.example.omar.logisticsapplication;


import android.content.Intent;
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
import com.example.omar.logisticsapplication.classes.AddCustomerInterface;
import com.example.omar.logisticsapplication.classes.Customer;
import com.example.omar.logisticsapplication.classes.EditCustomerInterface;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCustomer extends DialogFragment {

    public static final String TAG=EditCustomer.class.getSimpleName();
    private EditCustomerInterface mListener;

    private EditText user_name;
    private EditText inpMail;
    private EditText inpMobile;
    private EditText address;
    private Button edit_user;
    private Button cancel;

    private Customer customer;

    public EditCustomer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_customer, container, false);

        user_name=view.findViewById(R.id.txt_customer_name);
        inpMail=view.findViewById(R.id.txt_customer_mail);
        inpMobile =view.findViewById(R.id.txt_customer_mobile);
        address =view.findViewById(R.id.txt_customer_address);
        edit_user=view.findViewById(R.id.edit_customer);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserDB();
            }
        });

        setCustomer();
        return view;
    }

    public void setListener(EditCustomerInterface listener) {
        mListener = listener;
    }

    public void setCustomer(){
        Intent i = getActivity().getIntent();
        customer = (Customer) i.getSerializableExtra("customer");
        user_name.setText(customer.getName());
        inpMail.setText(customer.getEmail());
        inpMobile.setText(customer.getMobile());
        address.setText(customer.getAddress());
    }

    public void editUserDB(){
        String name = user_name.getText().toString().trim();
        String email = inpMail.getText().toString().trim();
        String phone = inpMobile.getText().toString().trim();
        String myAddress = address.getText().toString().trim();
        //String compID= ""+MainActivity.COMPANY_ID;

        if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !myAddress.isEmpty()) {
            editUser(name, email, phone, myAddress);
        } else {
            Toast.makeText(getContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void editUser(final String name, final String email,
                              final String phone,final String address) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_EDITCUSTOMER_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //check for success first
                Customer c=new Customer(name, email, phone, address);
                mListener.onEditClick(c);
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
                params.put("newname", name);
                params.put("oldname", customer.getName());
                params.put("email", email);
                params.put("mobile", phone);
                params.put("address", address);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}