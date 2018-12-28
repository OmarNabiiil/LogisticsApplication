package com.example.omar.logisticsapplication.listsAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.SupplierOrder;

import java.util.List;

public class SuppliersOrdersAdapter extends RecyclerView.Adapter<SuppliersOrdersAdapter.MyViewHolder> {
    private List<SupplierOrder> suppliersordersList;
    private SuppliersOrdersAdapter.ButtonClickListener monClickListener;

    public interface ButtonClickListener{
        void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
        void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name, mobile, address, product_name, quantity, price, date, transportation_status;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.customer_name);
            product_name=view.findViewById(R.id.product_name);
            mobile=view.findViewById(R.id.customer_mobile);
            address=view.findViewById(R.id.customer_address);
            quantity=view.findViewById(R.id.quantity);
            price=view.findViewById(R.id.price);
            date=view.findViewById(R.id.created_time);
            transportation_status=view.findViewById(R.id.transportation_status);
        }

    }

    public SuppliersOrdersAdapter(List<SupplierOrder> suppliersordersList) {
        this.suppliersordersList = suppliersordersList;
    }

    @Override
    public SuppliersOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suppliers_orders_list_item, parent, false);

        return new SuppliersOrdersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SuppliersOrdersAdapter.MyViewHolder holder, int position) {
        SupplierOrder s=suppliersordersList.get(position);
        holder.name.setText(s.getSupplierName());
        holder.product_name.setText(s.getProductName());
        holder.mobile.setText(s.getMobile());
        holder.address.setText(s.getAddress());
        holder.quantity.setText(s.getQuantity());
        holder.price.setText(s.getPrice());
        holder.date.setText(s.getDate());

        int transportation = Integer.parseInt(s.getTransportationStatus());
        switch(transportation){
            case 0: holder.transportation_status.setText("Not Delivered");
                holder.transportation_status.setTextColor(Color.RED);
                break;
            case 1: holder.transportation_status.setText("On Way");
                holder.transportation_status.setTextColor(Color.YELLOW);
                break;
            case 2: holder.transportation_status.setText("Delivered");
                holder.transportation_status.setTextColor(Color.GREEN);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return suppliersordersList.size();
    }

    public void removeItem(int position) {
        suppliersordersList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,suppliersordersList.size());
    }

    public void addItem(SupplierOrder o){
        suppliersordersList.add(o);
        notifyItemInserted(suppliersordersList.size()-1);
    }

    public void editItem(SupplierOrder o, int position) {
        suppliersordersList.set(position, o);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, suppliersordersList.size());
    }
}
