package com.example.omar.logisticsapplication.listsAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.ClientOrder;

import java.util.List;

/**
 * Created by Omar EL-Gendy on 4/8/2018.
 */

public class ClientsOrdersAdapter extends RecyclerView.Adapter<ClientsOrdersAdapter.MyViewHolder> {

    private List<ClientOrder> clientsordersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, mobile, address, product_name, quantity, date, transportation_status;
        private Button remove, edit;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.customer_name);
            product_name=view.findViewById(R.id.product_name);
            mobile=view.findViewById(R.id.customer_mobile);
            address=view.findViewById(R.id.customer_address);
            quantity=view.findViewById(R.id.quantity);
            date=view.findViewById(R.id.created_time);
            transportation_status=view.findViewById(R.id.transportation_status);
            remove=view.findViewById(R.id.remove_customer);
            edit=view.findViewById(R.id.edit_customer);
        }

    }

    public ClientsOrdersAdapter(List<ClientOrder> clientsordersList) {
        this.clientsordersList = clientsordersList;
    }

    @Override
    public ClientsOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clients_orders_list_item, parent, false);

        return new ClientsOrdersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClientsOrdersAdapter.MyViewHolder holder, int position) {
        ClientOrder s=clientsordersList.get(position);
        holder.name.setText(s.getCustomerName());
        holder.product_name.setText(s.getProductName());
        holder.mobile.setText(s.getMobile());
        holder.address.setText(s.getAddress());
        holder.quantity.setText(s.getQuantity());
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
        return clientsordersList.size();
    }

    public void removeItem(int position) {
        clientsordersList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,clientsordersList.size());
    }

    public void addItem(ClientOrder o){
        clientsordersList.add(o);
        notifyItemInserted(clientsordersList.size()-1);
    }

    public void editItem(ClientOrder o, int position) {
        clientsordersList.set(position, o);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, clientsordersList.size());
    }

}
