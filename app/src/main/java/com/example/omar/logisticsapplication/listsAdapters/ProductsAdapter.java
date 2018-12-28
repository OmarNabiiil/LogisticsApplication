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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private List<Product> productsList;
    private ProductsAdapter.ButtonClickListener monClickListener;

    public interface ButtonClickListener{
        void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
        void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, quantity, price, customerPrice, category;
        private ImageView status;
        private Button remove, edit;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.product_name);
            quantity=view.findViewById(R.id.quantity);
            price=view.findViewById(R.id.product_price);
            customerPrice=view.findViewById(R.id.customer_product_price);
            category=view.findViewById(R.id.category);
            status=view.findViewById(R.id.product_status);
            remove=view.findViewById(R.id.remove_product);
            edit=view.findViewById(R.id.edit_product);
            remove.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            if(view.equals(remove)){
                monClickListener.onRemoveClickListener(this, clickedItemPosition);
            }

            if(view.equals(edit)){
                monClickListener.onEditClickListener(this, clickedItemPosition);
            }
        }
    }

    public ProductsAdapter( List<Product> productsList, ProductsAdapter.ButtonClickListener listener) {
        this.productsList = productsList;
        monClickListener=listener;
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.MyViewHolder holder, int position) {
        Product p=productsList.get(position);
        holder.name.setText(p.getName());
        holder.quantity.setText(p.getQuantity());
        holder.price.setText(p.getPrice());
        holder.customerPrice.setText(p.getCustomer_price());
        holder.category.setText(p.getCategory());
        if(p.getState().equals("Active")){
            holder.status.setBackgroundColor(Color.GREEN);
        }else{
            holder.status.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void removeItem(int position) {
        productsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,productsList.size());
    }

    public void addItem(Product p){
        productsList.add(p);
        notifyItemInserted(productsList.size()-1);
    }

    public void editItem(Product p, int position) {
        productsList.set(position, p);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, productsList.size());
    }

}
