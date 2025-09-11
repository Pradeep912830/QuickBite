package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {
    ImageView foodImage, backButton;
    TextView foodName, foodprice, description;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        foodImage = findViewById(R.id.foodImage);
        foodName = findViewById(R.id.foodName);
        backButton = findViewById(R.id.backButton);
        foodprice = findViewById(R.id.price);
        description = findViewById(R.id.description);

        backButton.setOnClickListener(v -> onBackPressed());

        String name = getIntent().getStringExtra("foodName");
        String price = getIntent().getStringExtra("foodPrice");
        String imageUrl = getIntent().getStringExtra("foodImageUrl");
        String des = getIntent().getStringExtra("description");

        if(name != null){
            foodName.setText(name);
        }

        if(foodprice != null){
            foodprice.setText(price);
        }

        if(imageUrl != null){
            Glide.with(this).load(imageUrl).into(foodImage);
        }

        if(des != null){
            description.setText(des);
        }
    }

}
