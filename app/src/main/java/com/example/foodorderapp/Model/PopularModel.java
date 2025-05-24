package com.example.foodorderapp.Model;

public class PopularModel {

    private int food_image;
    private String food_name;
    private String food_price;
    private String food_addtocart;

    public PopularModel(int food_image, String food_name, String food_price, String food_addtocart) {
        this.food_image = food_image;
        this.food_name = food_name;
        this.food_price = food_price;
        this.food_addtocart = food_addtocart;
    }

    public int getImageResId() {
        return food_image;
    }

    public String getName() {
        return food_name;
    }

    public String getPrice() {
        return food_price;
    }

    public String getFood_addtocartName() {
        return food_addtocart;
    }
}
