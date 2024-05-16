package com.example.boycott_food.view;



public class Product {

    private String productName;
    private String brandName;
    private boolean boycottStatus; // Add this field

    public Product(String productName, String brandName, boolean boycottStatus) {
        this.productName = productName;
        this.brandName = brandName;
        this.boycottStatus = boycottStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public boolean isBoycottStatus() {
        return boycottStatus;
    }
}

