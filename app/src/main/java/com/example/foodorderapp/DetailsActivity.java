package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    ImageView foodImage, backButton;
    TextView foodName, foodPrice, description, ingredient;
    AppCompatButton addToCart;
    DatabaseReference reference;
    FirebaseAuth auth;

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
        addToCart = findViewById(R.id.addToCart);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

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
        foodPrice.setText(price != null ? price : "Price not available");
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

        // Add to cart functionality

        String finalName = name;
        String finalPrice = price;
        String finalImageUrl = imageUrl;
        String finalDes = des;
        String finalIngredients = ingredients;

        addToCart.setOnClickListener(v->{
            String userid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
            if(userid == null){
                Toast.makeText(DetailsActivity.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                return;
            }

            String cartItemId = reference.child(userid).child("cartItems").push().getKey();

            Map<String, Object> cartItem = new HashMap<>();

            cartItem.put("id", cartItemId);
            cartItem.put("name", finalName);
            try {
                double priceValue = Double.parseDouble(finalPrice);
                cartItem.put("price", priceValue);
            } catch (NumberFormatException e){
                cartItem.put("price", 0.0);
            }
            cartItem.put("imageUrl", finalImageUrl);
            cartItem.put("description", finalDes);
            cartItem.put("ingredients", finalIngredients);

            if(cartItemId != null){
                reference.child(userid).child("cartItems").child(cartItemId)
                        .setValue(cartItem).addOnSuccessListener(unused -> Toast.makeText(DetailsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e ->
                                Toast.makeText(DetailsActivity.this, "Failed: "+ e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

    }
}
