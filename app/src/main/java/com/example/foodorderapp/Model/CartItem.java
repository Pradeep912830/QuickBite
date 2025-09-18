package com.example.foodorderapp.Model;

import com.google.firebase.database.Exclude;

public class CartItem {
    private String id;
    private String name;
    private double price;
    private String imageUrl;

    @Exclude
    private int quantity = 1; // default only in app

    public CartItem() {
        // empty constructor for Firebase
    }

    public CartItem(String id, String name, double
            price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = 1;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }

     public void setPrice(Object price){
        if(price instanceof Number){
            this.price = ((Number) price).doubleValue();
        }else{
            try {
                this.price = Double.parseDouble(price.toString());
            }catch (Exception e){
                this.price = 0.0;
            }
        }
     }
    public int getQuantity() { return quantity; }

    @Exclude
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
