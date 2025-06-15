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
        int imageResId = getIntent().getIntExtra("foodImage", 0);

        foodName.setText(name);
        foodImage.setImageResource(imageResId);
    }

}
