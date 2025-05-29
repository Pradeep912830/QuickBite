package com.example.foodorderapp.Model;

public class MenuItemModel {
    private String name;
    private String price;
    private int imageResId;

    public MenuItemModel(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
