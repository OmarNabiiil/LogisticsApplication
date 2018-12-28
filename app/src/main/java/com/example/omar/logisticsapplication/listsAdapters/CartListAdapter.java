package com.example.omar.logisticsapplication.listsAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Product;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>  {

    public CartListAdapter(List<Product>products_list) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, quantity, price;
        private ImageView status;
        private Button remove, edit;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.product_name);
            quantity=view.findViewById(R.id.quantity);
            price=view.findViewById(R.id.product_price);
            status=view.findViewById(R.id.product_status);
            remove=view.findViewById(R.id.remove_product);
            edit=view.findViewById(R.id.edit_product);
        }

    }

    @Override
    public CartListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CartListAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
