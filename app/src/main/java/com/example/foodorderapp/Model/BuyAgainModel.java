package com.example.foodorderapp.Model;

public class BuyAgainModel {
    private int image;
    private String name;
    private String price;

    public BuyAgainModel(int image, String name, String price){
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
