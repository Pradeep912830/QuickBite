package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    ImageView foodImage, backButton;
    TextView foodName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        foodImage = findViewById(R.id.foodImage);
        foodName = findViewById(R.id.foodName);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> onBackPressed());

        String name = getIntent().getStringExtra("foodName");
        int imageResId = getIntent().getIntExtra("foodImage", -1);

        if (name != null && imageResId != -1) {
            foodName.setText(name);
            foodImage.setImageResource(imageResId);
        } else {
            // Try the alternative keys
            String name1 = getIntent().getStringExtra("food_name");
            int imageResId1 = getIntent().getIntExtra("food_image", -1);

            if (name1 != null && imageResId1 != -1) {
                foodName.setText(name1);
                foodImage.setImageResource(imageResId1);
            } else {
                foodName.setText("No food data received");
            }
        }

    }

}
