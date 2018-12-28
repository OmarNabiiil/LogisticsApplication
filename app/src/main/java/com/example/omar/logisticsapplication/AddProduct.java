package com.example.omar.logisticsapplication;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.example.omar.logisticsapplication.classes.AddProductInterface;
import com.example.omar.logisticsapplication.classes.Product;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends DialogFragment {

    public static final String TAG=AddProduct.class.getSimpleName();
    private AddProductInterface mListener;

    private EditText product_name;
    private EditText product_quantity;
    private EditText product_price;
    private EditText customer_price;
    private EditText product_category;
    private Button add_product;
    private Button cancel;

    public AddProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_product, container, false);

        product_name=view.findViewById(R.id.txt_product_name);
        product_quantity=view.findViewById(R.id.txt_product_quantity);
        product_price =view.findViewById(R.id.txt_product_price);
        customer_price =view.findViewById(R.id.txt_customer_price);
        product_category =view.findViewById(R.id.txt_product_category);
        add_product=view.findViewById(R.id.add_product);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserDB();
            }
        });

        return view;
    }

    public void setListener(AddProductInterface listener) {
        mListener = listener;
    }

    public void addUserDB(){
        String name = product_name.getText().toString().trim();
        String quantity = product_quantity.getText().toString().trim();
        int prod_quantity=Integer.parseInt(quantity);
        String price = product_price.getText().toString().trim();
        String customerPrice = customer_price.getText().toString().trim();
        String category = product_category.getText().toString().trim();

        if (!name.isEmpty() && !quantity.isEmpty() && !price.isEmpty() && !customerPrice.isEmpty() && !category.isEmpty()) {
            addProduct(name, prod_quantity, price, customerPrice, category);
        } else {
            Toast.makeText(getContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void addProduct(final String name, final int quantity, final String price,
                            final String customerPrice, final String category) {
        String tag_string_req = "req_register";
        String url = Config.TEST_ADDPRODUCT_URL;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //check for success first
                Product c=new Product(name, quantity+"", price, customerPrice, category);
                mListener.onAddClick(c);
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
                params.put("quantity", quantity+"");
                params.put("price", price);
                params.put("customerPrice", customerPrice);
                params.put("category", category);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
