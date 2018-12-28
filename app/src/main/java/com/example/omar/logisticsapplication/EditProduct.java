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
import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.classes.EditProductInterface;

import java.util.HashMap;
import java.util.Map;

public class EditProduct extends DialogFragment {

    public static final String TAG=EditProduct.class.getSimpleName();
    private EditProductInterface mListener;
    private EditText product_name;
    private EditText product_quantity;
    private EditText product_price;
    private EditText customer_price;
    private EditText product_category;
    private Button edit_product;
    private Button cancel;
    private Product product;

    public EditProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_product, container, false);

        product_name=view.findViewById(R.id.txt_product_name);
        product_quantity=view.findViewById(R.id.txt_product_quantity);
        product_price =view.findViewById(R.id.txt_product_price);
        customer_price =view.findViewById(R.id.txt_customer_price);
        product_category =view.findViewById(R.id.txt_product_category);
        edit_product=view.findViewById(R.id.edit_product);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        edit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editproductDB();
            }
        });

        setProduct();
        return view;
    }

    public void setListener(EditProductInterface listener) {
        mListener = listener;
    }

    public void setProduct(){
        Intent i = getActivity().getIntent();
        product = (Product) i.getSerializableExtra("Product");
        product_name.setText(product.getName());
        product_quantity.setText(product.getQuantity());
        product_price.setText(product.getPrice());
    }

    public void editproductDB(){
        String name = product_name.getText().toString().trim();
        String quantity = product_quantity.getText().toString().trim();
        String price = product_price.getText().toString().trim();
        String customerPrice = customer_price.getText().toString().trim();
        String category = product_category.getText().toString().trim();

        if (!name.isEmpty() && !quantity.isEmpty() && !price.isEmpty() && !customerPrice.isEmpty() && !category.isEmpty()) {
            editproduct(name, quantity, price, customerPrice, category);
        } else {
            Toast.makeText(getContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void editproduct(final String name, final String quantity, final String price,
                             final String customerPrice, final String category) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_EDITPRODUCT_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //check for success first
                Product c=new Product(name, quantity, price, customerPrice, category);
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
                params.put("oldname", product.getName());
                params.put("quantity", quantity);
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