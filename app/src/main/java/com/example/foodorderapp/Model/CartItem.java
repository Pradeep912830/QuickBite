package com.example.foodorderapp.Model;

import com.google.firebase.database.Exclude;

public class CartItem {
    private String name;
    private String price;
    private String imageUrl;

    @Exclude
    private int quantity = 1; // default only in app

    public CartItem() {
        // empty constructor for Firebase
    }

    public CartItem(String name, String price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = 1;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }

    @Exclude
    public int getQuantity() { return quantity; }

    @Exclude
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
