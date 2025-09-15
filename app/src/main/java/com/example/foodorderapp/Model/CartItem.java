package com.example.foodorderapp.Model;

public class CartItem {
    private String name;
    private String price;
    private String imageUrl;

    public CartItem(){

    }

    public CartItem(String name, String price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;

    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
//    public int getQuantity() { return quantity; }
//
//    public void setQuantity(int quantity) { this.quantity = quantity; }
}
