package com.example.omar.logisticsapplication.listsAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Driver;

import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.MyViewHolder> {

    private List<Driver> driversList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, mobile, address, costPerHour, status;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.driver_name);
            mobile=view.findViewById(R.id.driver_mobile);
            address=view.findViewById(R.id.driver_address);
            costPerHour=view.findViewById(R.id.cost);
            status=view.findViewById(R.id.driver_status);
        }

    }

    public DriversAdapter( List<Driver> driversList ) {
        this.driversList = driversList;
    }

    @Override
    public DriversAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drivers_list_item, parent, false);

        return new DriversAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DriversAdapter.MyViewHolder holder, int position) {
        Driver p=driversList.get(position);
        holder.name.setText(p.getName());
        holder.mobile.setText(p.getMobile());
        holder.address.setText(p.getAddress());
        holder.costPerHour.setText(p.getCost());
        int driverStatus = Integer.parseInt(p.getDriverStatus());
        switch(driverStatus){
            case 0: holder.status.setText("Busy");
                holder.status.setTextColor(Color.RED);
                break;
            case 1: holder.status.setText("Available");
                holder.status.setTextColor(Color.GREEN);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return driversList.size();
    }

    public void removeItem(int position) {
        driversList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,driversList.size());
    }

    public void addItem(Driver p){
        driversList.add(p);
        notifyItemInserted(driversList.size()-1);
    }

    public void editItem(Driver p, int position) {
        driversList.set(position, p);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, driversList.size());
    }

}
