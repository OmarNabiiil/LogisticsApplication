package com.example.omar.logisticsapplication;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.AddSupplierOrderInterface;
import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.classes.Supplier;
import com.example.omar.logisticsapplication.classes.SupplierOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSupplierOrderFragment extends DialogFragment {

    private Button make_order;
    private Button cancel;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private Spinner suppliers_spinner;
    private ArrayList<Product> products = new ArrayList<>();
    private Spinner products_spinner;
    private EditText quantity;
    private AddSupplierOrderInterface mIReminderAdded;

    public AddSupplierOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_supplier_order, container, false);

        quantity = view.findViewById(R.id.product_quantity);
        suppliers_spinner = view.findViewById(R.id.list_suppliers);
        products_spinner = view.findViewById(R.id.list_products);
        make_order=view.findViewById(R.id.btn_add_order);
        cancel=view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        make_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderData();
            }
        });

        getAllProducts();
        getAllSuppliers();

        return view;
    }

    private void getOrderData() {

        int selectedSupplierPosition = suppliers_spinner.getSelectedItemPosition();
        int selectedProductPosition = products_spinner.getSelectedItemPosition();
        Product selectedProduct = products.get(selectedProductPosition);
        Supplier selectedSupplier = suppliers.get(selectedSupplierPosition);
        int q = Integer.parseInt(quantity.getText().toString());
        double price = Integer.parseInt(selectedProduct.getPrice());
        double totalPrice = q*price;
        addOrder(selectedSupplier.getName(), selectedSupplier.getMobile(), selectedSupplier.getAddress(),
                selectedProduct.getName(), quantity.getText().toString(), totalPrice+"");

    }

    public void addOrder(final String supplierName, final String mobile, final String address, final String productName, final String quantity, final String price){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_ADDSUPPLIERORDER_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("testttt",""+response);
                SupplierOrder r=new SupplierOrder(supplierName, mobile, address, productName, quantity, price, "0" );
                mIReminderAdded.onAddClick(r);
                dismiss();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", supplierName);
                params.put("product", productName);
                params.put("transportation", "0");
                params.put("phone", mobile);
                params.put("quantity", quantity);
                params.put("price", price);
                params.put("address", address);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getAllSuppliers(){
        // Tag used to cancel the request
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
                    ArrayList<String> mySuppliers=new ArrayList<String>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Supplier c=new Supplier(jObj.get("name").toString(), jObj.get("email").toString(),
                                jObj.get("mobile").toString(), jObj.get("address").toString());
                        suppliers.add(c);
                        mySuppliers.add(jObj.get("name").toString());

                    }

                    ArrayAdapter<String> Suppliers_adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, mySuppliers);
                    suppliers_spinner.setAdapter(Suppliers_adapter);
                    Suppliers_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    public void getAllProducts(){

        String tag_string_req = "req_register";

        String url = Config.TEST_PRODUCT_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    ArrayList<String> myProducts=new ArrayList<String>();
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Product c=new Product(jObj.get("product name").toString(), jObj.get("available quantity").toString(),
                                jObj.get("product price").toString(), jObj.get("customers price").toString(), jObj.get("category").toString());
                        products.add(c);
                        myProducts.add(c.getName());

                    }

                    ArrayAdapter<String> Suppliers_adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, myProducts);
                    products_spinner.setAdapter(Suppliers_adapter);
                    Suppliers_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

    public void setListener(SuppliersOrdersActivity suppliersOrdersActivity) {
        mIReminderAdded = suppliersOrdersActivity;
    }
}
