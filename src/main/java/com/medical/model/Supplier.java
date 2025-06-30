package com.medical.model;

/**
 * Supplier model class
 */
public class Supplier {
    private String name;
    private long contactNo;
    private String emailId;
    private String place;
    private String suppliedProduct;

    public Supplier() {}

    public Supplier(String name, long contactNo, String emailId, String place, String suppliedProduct) {
        this.name = name;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.place = place;
        this.suppliedProduct = suppliedProduct;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getContactNo() { return contactNo; }
    public void setContactNo(long contactNo) { this.contactNo = contactNo; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getSuppliedProduct() { return suppliedProduct; }
    public void setSuppliedProduct(String suppliedProduct) { this.suppliedProduct = suppliedProduct; }
}