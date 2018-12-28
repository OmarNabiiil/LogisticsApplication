package com.example.omar.logisticsapplication.classes;

import java.io.Serializable;

/**
 * Created by Omar EL-Gendy on 4/7/2018.
 */

public class Supplier implements Serializable {

    private String name;
    private String email;
    private String mobile;
    private String address;

    public Supplier(String name, String email, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
