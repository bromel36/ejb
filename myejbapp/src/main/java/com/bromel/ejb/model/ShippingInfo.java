package com.bromel.ejb.model;


import java.io.Serializable;

public class ShippingInfo implements Serializable {
    private String name;
    private String address;
    private String phone;

    public ShippingInfo(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}
