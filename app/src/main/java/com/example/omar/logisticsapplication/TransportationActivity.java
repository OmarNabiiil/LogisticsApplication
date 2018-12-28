package com.example.omar.logisticsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.omar.logisticsapplication.CustomerOrder.ClientsTransportationActivity;

public class TransportationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

    }

    public void SuppliersTransportation(View view) {
        Intent i = new Intent(TransportationActivity.this, SuppliersTransportationActivity.class);
        startActivity(i);
    }

    public void ClientsTransportation(View view) {
        Intent i = new Intent(TransportationActivity.this, ClientsTransportationActivity.class);
        startActivity(i);
    }

    public void Drivers(View view) {
        Intent i = new Intent(TransportationActivity.this, DriversActivity.class);
        startActivity(i);
    }
}
