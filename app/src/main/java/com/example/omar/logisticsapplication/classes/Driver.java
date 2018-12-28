package com.example.omar.logisticsapplication.classes;

public class Driver {

    private String name;
    private String mobile;
    private String address;
    private String cost;
    private String driverStatus;

    public Driver(String name, String mobile, String address, String cost, String driverStatus) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.cost = cost;
        this.driverStatus = driverStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }
}
