package com.example.omar.logisticsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.listsAdapters.CustomerProductsAdapter;
import com.example.omar.logisticsapplication.listsAdapters.ProductsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerPanelActivity extends AppCompatActivity implements CustomerProductsAdapter.ButtonClickListener {

    private RecyclerView recyclerView;
    private List<Product> products_list;
    private CustomerProductsAdapter mAdapter;
    private List<Product> cart_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);

        recyclerView = findViewById(R.id.recycler_view);

        cart_products=new ArrayList<>();
        products_list = new ArrayList<>();
        mAdapter = new CustomerProductsAdapter( products_list,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllProducts();
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
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Product c=new Product(jObj.get("product name").toString(), jObj.get("available quantity").toString(),
                                jObj.get("product price").toString(), jObj.get("customer price").toString(), jObj.get("category").toString());
                        products_list.add(c);
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
    public void onAddToCartClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Product p=products_list.get(viewHolder.getAdapterPosition());
        cart_products.add(p);
    }
}
