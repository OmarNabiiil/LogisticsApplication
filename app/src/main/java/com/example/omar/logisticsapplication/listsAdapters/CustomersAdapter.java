package com.example.omar.logisticsapplication.listsAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Customer;
import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> {

    private List<Customer> customersList;
    private ButtonClickListener monClickListener;

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

    public CustomersAdapter( List<Customer> customersList, ButtonClickListener listener) {
        this.customersList = customersList;
        monClickListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customers_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Customer c=customersList.get(position);
        holder.name.setText(c.getName());
        holder.mail.setText(c.getEmail());
        holder.mobile.setText(c.getMobile());
        holder.address.setText(c.getAddress());
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public void removeItem(int position) {
        customersList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,customersList.size());
    }

    public void addItem(Customer c){
        customersList.add(c);
        notifyItemInserted(customersList.size()-1);
    }

    public void editItem(Customer c, int position) {
        customersList.set(position, c);
        notifyItemChanged(position);
        notifyItemRangeChanged(position,customersList.size());
    }

}