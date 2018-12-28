package com.example.omar.logisticsapplication.listsAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Product;

import java.util.List;

/**
 * Created by Omar EL-Gendy on 4/20/2018.
 */

public class CustomerProductsAdapter extends RecyclerView.Adapter<CustomerProductsAdapter.MyViewHolder> {

    private List<Product> CustomerproductsList;
    private CustomerProductsAdapter.ButtonClickListener monClickListener;

    public interface ButtonClickListener{
        void onAddToCartClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, description, category, price;
        private Button addToCart;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.product_name);
            description=view.findViewById(R.id.txt_description);
            price=view.findViewById(R.id.product_price);
            category=view.findViewById(R.id.category);
            addToCart=view.findViewById(R.id.remove_product);
            addToCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            if(view.equals(addToCart)){
                monClickListener.onAddToCartClickListener(this, clickedItemPosition);
            }
        }
    }

    public CustomerProductsAdapter(List<Product> CustomerproductsList, CustomerProductsAdapter.ButtonClickListener listener) {
        this.CustomerproductsList = CustomerproductsList;
        monClickListener=listener;
    }

    @Override
    public CustomerProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_products_list_item, parent, false);

        return new CustomerProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomerProductsAdapter.MyViewHolder holder, int position) {
        Product p=CustomerproductsList.get(position);
        holder.name.setText(p.getName());
        holder.description.setText(p.getQuantity());
        holder.price.setText(p.getPrice());
        holder.category.setText(p.getCategory());
    }

    @Override
    public int getItemCount() {
        return CustomerproductsList.size();
    }

}

