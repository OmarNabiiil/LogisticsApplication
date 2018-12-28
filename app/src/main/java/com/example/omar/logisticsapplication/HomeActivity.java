package com.example.omar.logisticsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Suppliers(View view) {
        //actions when the supplier button is clicked
        Intent i=new Intent(HomeActivity.this, SuppliersActivity.class);
        startActivity(i);
    }

    public void Products(View view) {
        //actions when the materials button is clicked
        Intent i=new Intent(HomeActivity.this, ProductsActivity.class);
        startActivity(i);
    }

    public void Orders(View view) {
        //actions when the order button is clicked
        Intent i=new Intent(HomeActivity.this, OrdersActivity.class);
        startActivity(i);
    }

    public void Transportation(View view) {
        //actions when the transportation button is clicked
        Intent i=new Intent(HomeActivity.this, TransportationActivity.class);
        startActivity(i);
    }

    public void Customers(View view) {
        //actions when the customers button is clicked
        Intent i=new Intent(HomeActivity.this, CustomersActivity.class);
        startActivity(i);
    }

    public void Logout(View view) {
        //actions when the logout button is clicked
        Intent i=new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
