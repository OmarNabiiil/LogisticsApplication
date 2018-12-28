package com.example.omar.logisticsapplication.listsAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Supplier;

import java.util.List;

/**
 * Created by Omar EL-Gendy on 4/8/2018.
 */

public class SuppliersAdapter extends RecyclerView.Adapter<SuppliersAdapter.MyViewHolder> {

    private List<Supplier> suppliersList;
    private SuppliersAdapter.ButtonClickListener monClickListener;

    public interface ButtonClickListener{
        void onRemoveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
        void onEditClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, mail, mobile, address;
        private Button remove, edit;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.customer_name);
            mail=view.findViewById(R.id.customer_mail);
            mobile=view.findViewById(R.id.customer_mobile);
            address=view.findViewById(R.id.customer_address);
            remove=view.findViewById(R.id.remove_customer);
            edit=view.findViewById(R.id.edit_customer);
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

    public SuppliersAdapter( List<Supplier> suppliersList, SuppliersAdapter.ButtonClickListener listener) {
        this.suppliersList = suppliersList;
        monClickListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suppliers_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Supplier s=suppliersList.get(position);
        holder.name.setText(s.getName());
        holder.mail.setText(s.getEmail());
        holder.mobile.setText(s.getMobile());
        holder.address.setText(s.getAddress());
    }

    @Override
    public int getItemCount() {
        return suppliersList.size();
    }

    public void removeItem(int position) {
        suppliersList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,suppliersList.size());
    }

    public void addItem(Supplier s){
        suppliersList.add(s);
        notifyItemInserted(suppliersList.size()-1);
    }

    public void editItem(Supplier s, int position) {
        suppliersList.set(position, s);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, suppliersList.size());
    }

}
