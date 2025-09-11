package com.example.foodorderapp.Model;

public class MenuItemModel {
    private String name;
    private String price;
    private String imageUrl;
    private String description;

    public MenuItemModel(){

    }

    public MenuItemModel(String name, String price, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public  String getDescription(){
        return description;
    }
}
