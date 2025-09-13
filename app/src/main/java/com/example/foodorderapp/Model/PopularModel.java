package com.example.foodorderapp.Model;

public class PopularModel {

    private String name;
    private String price;
    private String ingredients;
    private String imageUrl;
    private String description;
    public PopularModel() {
        // Required empty constructor for Firebase
    }

    public PopularModel(String name, String price, String ingredients, String imageUrl, String description){
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public String getDescription(){
        return description;
    }
    public String getIngredients(){
        return ingredients;
    }

    public String getImageUrl(){
        return imageUrl;
    }
}

