package com.example.foodorderapp.Model;

public class FoodItem {
    private String name;
    private String price;
    private int image;

    public FoodItem(String name, String price, int image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return image;
    }
}
