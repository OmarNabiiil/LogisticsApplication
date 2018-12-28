package com.example.omar.logisticsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.omar.logisticsapplication.CustomerOrder.*;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
    }

    public void ClientsOrders(View view) {
        Intent i = new Intent(OrdersActivity.this, ClientsOrdersActivity.class);
        startActivity(i);
    }

    public void SuppliersOrders(View view) {
        Intent i = new Intent(OrdersActivity.this, SuppliersOrdersActivity.class);
        startActivity(i);
    }
}
