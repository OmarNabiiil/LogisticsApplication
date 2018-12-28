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
import com.example.omar.logisticsapplication.classes.AddProductInterface;
import com.example.omar.logisticsapplication.classes.Product;
import com.example.omar.logisticsapplication.classes.EditProductInterface;
import com.example.omar.logisticsapplication.listsAdapters.ProductsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity implements ProductsAdapter.ButtonClickListener,
        AddProductInterface, EditProductInterface {

    private RecyclerView recyclerView;
    private List<Product> products_list;
    private ProductsAdapter mAdapter;
    private int edit_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recycler_view);

        products_list = new ArrayList<>();
        mAdapter = new ProductsAdapter( products_list,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllProducts();
    }

    @Override
    public void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Product c=products_list.get(viewHolder.getAdapterPosition());
        removeProduct(c.getName(), viewHolder.getAdapterPosition());
    }

    @Override
    public void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Product c=products_list.get(viewHolder.getAdapterPosition());
        edit_position=viewHolder.getAdapterPosition();
        editProduct(c);
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
                                jObj.get("product price").toString(), jObj.get("customers price").toString(), jObj.get("category").toString());
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

    public void addProduct(){
        AddProduct fr=new AddProduct();
        fr.setListener(this);
        fr.show(getSupportFragmentManager(), "Add Product");
    }

    public void editProduct(Product p){
        EditProduct fr=new EditProduct();
        fr.setListener(this);
        getIntent().putExtra("Product", p);
        fr.show(getSupportFragmentManager(), "Edit Product");
    }

    public void removeProduct(final String name, final int position) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_REMOVEPRODUCT_URL;

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
    public void onAddClick(Product c) {
        mAdapter.addItem(c);
    }

    public void Add(View view) {
        addProduct();
    }

    @Override
    public void onEditClick(Product c) {
        mAdapter.editItem(c, edit_position);
    }
}
