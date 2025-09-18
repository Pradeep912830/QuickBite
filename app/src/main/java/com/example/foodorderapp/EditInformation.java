// EditInformation.java
package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorderapp.Fragment.HomeFragment;
import com.example.foodorderapp.Model.CartItem;
import com.example.foodorderapp.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditInformation extends AppCompatActivity {

    private EditText userName, userAddress, userPhone, totalPrice, totalQuantity;
    private ImageView goBack;
    private AppCompatButton placeMyOrder;

    private FirebaseAuth auth;
    private DatabaseReference userRef, orderRef;
    private double totalPriceValue;
    private int totalQuantityValue;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        placeMyOrder = findViewById(R.id.placeMyOrder);
        goBack = findViewById(R.id.goBack);
        userName = findViewById(R.id.userName);
        userAddress = findViewById(R.id.userAddress);
        userPhone = findViewById(R.id.userPhoneNumber);
        totalPrice = findViewById(R.id.totalPrice);
        totalQuantity = findViewById(R.id.totalQuantity);

        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        orderRef = FirebaseDatabase.getInstance().getReference("Orders");

        goBack.setOnClickListener(v -> onBackPressed());

        totalQuantityValue = getIntent().getIntExtra("totalQuantity", 0);
        totalPriceValue = getIntent().getIntExtra("totalPrice", 0);

        totalQuantity.setText(String.valueOf(totalQuantityValue));
        totalPrice.setText(String.valueOf(totalPriceValue));


        String userId = auth.getCurrentUser().getUid();
        userRef.child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if(name != null){
                    userName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        placeMyOrder.setOnClickListener(v -> placeOrder(userId));
    }

    private void placeOrder(String userId){
        String name = userName.getText().toString().trim();
        String address = userAddress.getText().toString().trim();
        String phone = userPhone.getText().toString().trim();

        if(name.isEmpty() || address.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = orderRef.push().getKey();
        DatabaseReference cartRef = userRef.child(userId).child("cartItems");

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, CartItem> items = new HashMap<>();
                for(DataSnapshot itemSnap : snapshot.getChildren()){
                    CartItem item = itemSnap.getValue(CartItem.class);

                    if(item != null){
                        items.put(itemSnap.getKey(), item);
                    }
                }

                if(items.isEmpty()){
                    Toast.makeText(EditInformation.this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Order order = new Order(orderId, userId, items,
                        totalPriceValue, totalQuantityValue,
                        "Pending", System.currentTimeMillis(),
                        name, address,phone);

                orderRef.child(userId).child(orderId).setValue(order).addOnSuccessListener(aVoid ->{
                    Toast.makeText(EditInformation.this, "Order Placed!", Toast.LENGTH_SHORT).show();
                    cartRef.removeValue();
                    Intent intent = new Intent(EditInformation.this, CongratsActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e ->
                        Toast.makeText(EditInformation.this, "failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
