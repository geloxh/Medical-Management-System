package com.medical.model;

import java.time.LocalDate;

/**
 * Product model class representing medical products
 */
public class Product {
    private int productId;
    private String productName;
    private String productType;
    private LocalDate expiryDate;
    private String company;
    private String storedAt;
    private int quantity;
    private double price;

    public Product() {}

    public Product(int productId, String productName, String productType, 
                   LocalDate expiryDate, String company, String storedAt, 
                   int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.expiryDate = expiryDate;
        this.company = company;
        this.storedAt = storedAt;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getStoredAt() { return storedAt; }
    public void setStoredAt(String storedAt) { this.storedAt = storedAt; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}