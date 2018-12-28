package com.example.omar.logisticsapplication.listsAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.Transportation;

import java.util.List;

/**
 * Created by Omar EL-Gendy on 5/6/2018.
 */

public class TransportationsAdapter extends RecyclerView.Adapter<TransportationsAdapter.MyViewHolder> {

    private List<Transportation> transportationsList;
    private TransportationsAdapter.ArrivedClickListener monClickListener;

    public interface ArrivedClickListener{
        void onArrivedClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, orderNo, mobile, startTime;
        private Button arrived;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.driver_name);
            orderNo=view.findViewById(R.id.orderNo);
            mobile=view.findViewById(R.id.driver_mobile);
            startTime=view.findViewById(R.id.startTime);
            arrived=view.findViewById(R.id.btn_arrived);
            arrived.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            if(view.equals(arrived)){
                monClickListener.onArrivedClickListener(this, clickedItemPosition);
            }
        }
    }

    public TransportationsAdapter( List<Transportation> transportationsList, TransportationsAdapter.ArrivedClickListener listener) {
        this.transportationsList = transportationsList;
        monClickListener=listener;
    }

    @Override
    public TransportationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transportations_list_item, parent, false);

        return new TransportationsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TransportationsAdapter.MyViewHolder holder, int position) {
        Transportation s=transportationsList.get(position);
        holder.name.setText(s.getDriver().getName());
        holder.orderNo.setText(s.getOrderNumber());
        holder.mobile.setText(s.getDriver().getMobile());
        holder.startTime.setText(s.getStartTime());
    }

    @Override
    public int getItemCount() {
        return transportationsList.size();
    }

    public void removeItem(int position) {
        transportationsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,transportationsList.size());
    }

    public void addItem(Transportation s){
        transportationsList.add(s);
        notifyItemInserted(transportationsList.size()-1);
    }

    public void editItem(Transportation s, int position) {
        transportationsList.set(position, s);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, transportationsList.size());
    }

}
