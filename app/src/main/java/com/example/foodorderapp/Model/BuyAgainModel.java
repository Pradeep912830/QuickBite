package com.example.foodorderapp.Model;

public class BuyAgainModel {
    private String image;
    private String name;
    private String price;

    public BuyAgainModel(){

    }

    public BuyAgainModel(String image, String name, String price){
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImageUrl() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
