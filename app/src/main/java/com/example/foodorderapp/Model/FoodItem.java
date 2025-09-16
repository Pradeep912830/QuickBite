package com.example.foodorderapp.Model;

public class FoodItem {
    private String name;
    private String price;
    private String imageUrl;

    public FoodItem(){

    }

    public FoodItem(String name, String price, String imageUrl){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
