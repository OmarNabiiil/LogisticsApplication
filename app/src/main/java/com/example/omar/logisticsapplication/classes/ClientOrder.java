package com.example.omar.logisticsapplication.classes;

/**
 * Created by Omar EL-Gendy on 4/7/2018.
 */
//private TextView name, mail, mobile, address, product_name, quantity, date, transportation_status;
public class ClientOrder {

    private String id;
    private String customerName;
    private String mobile;
    private String address;
    private String productName;
    private String price;
    private String quantity;
    private String date;
    private String transportationStatus;

    public ClientOrder(String customerName, String mobile, String address, String productName, String quantity, String price, String transportationStatus) {
        this.customerName = customerName;
        this.mobile = mobile;
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.transportationStatus = transportationStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransportationStatus() {
        return transportationStatus;
    }

    public void setTransportationStatus(String transportationStatus) {
        this.transportationStatus = transportationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
