package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {
    ImageView foodImage, backButton;
    TextView foodName, foodPrice, description, ingredient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize views
        foodImage = findViewById(R.id.foodImage);
        foodName = findViewById(R.id.foodName);
        backButton = findViewById(R.id.backButton);
        foodPrice = findViewById(R.id.price);
        description = findViewById(R.id.description);
        ingredient = findViewById(R.id.ingredient);

        backButton.setOnClickListener(v -> onBackPressed());

        // Handle multiple key names from different activities
        String name = getIntent().getStringExtra("foodName");
        if (name == null) name = getIntent().getStringExtra("name");

        String price = getIntent().getStringExtra("foodPrice");
        if (price == null) price = getIntent().getStringExtra("price");

        String imageUrl = getIntent().getStringExtra("foodImageUrl");
        if (imageUrl == null) imageUrl = getIntent().getStringExtra("imageUrl");

        String des = getIntent().getStringExtra("description");
        String ingredients = getIntent().getStringExtra("ingredients");

        // Set data with null checks
        foodName.setText(name != null ? name : "Unknown Item");
        foodPrice.setText(price != null ? "₹ " + price : "Price not available");
        description.setText(des != null ? des : "No description available");
        ingredient.setText(ingredients != null ? ingredients : "No ingredients listed");

        // Load image (supports both URL and drawable resource)
        if (imageUrl != null) {
            if (imageUrl.startsWith("http")) {
                Glide.with(this).load(imageUrl).into(foodImage);
            } else {
                try {
                    int resId = Integer.parseInt(imageUrl); // if sent as string resourceId
                    foodImage.setImageResource(resId);
                } catch (Exception e) {
                    foodImage.setImageResource(R.drawable.food_three); // fallback
                }
            }
        } else {
            int resId = getIntent().getIntExtra("foodImage", R.drawable.food_three);
            foodImage.setImageResource(resId);
        }
    }
}
