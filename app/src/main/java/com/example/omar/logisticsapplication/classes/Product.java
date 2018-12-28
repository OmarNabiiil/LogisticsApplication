package com.example.omar.logisticsapplication.classes;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String quantity;
    private String price;
    private String customer_price;
    private String state;
    private String category;

    public Product(String name, String quantity, String price, String customerPrice, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.customer_price = customerPrice;
        this.category = category;
        int quan=Integer.parseInt(quantity);
        setState(quan>=10000?"Active":"Not active");
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomer_price() {
        return customer_price;
    }

    public void setCustomer_price(String customer_price) {
        this.customer_price = customer_price;
    }
}
