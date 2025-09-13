package com.example.foodorderapp.Model;

public class MenuItemModel {
    private String name;
    private String price;
    private String imageUrl;
    private String description;
    private String ingredients;

    public MenuItemModel(){

    }

    public MenuItemModel(String name, String price, String imageUrl, String description, String ingredients) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.ingredients = ingredients;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription(){
        return description;
    }

    public String getIngredients(){
        return ingredients;
    }
}
