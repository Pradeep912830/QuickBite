package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Choose_Location_Activity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    AppCompatButton btnLocation;



    private String[] locations = { "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad",
            "Chennai", "Kolkata", "Pune", "Jaipur", "Surat",
            "Lucknow", "Kanpur", "Nagpur", "Visakhapatnam", "Indore",
            "Thane", "Bhopal", "Patna", "Vadodara", "Ghaziabad",
            "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut",
            "Rajkot", "Kalyan-Dombivli", "Varanasi", "Srinagar", "Aurangabad",
            "Dhanbad", "Amritsar", "Navi Mumbai", "Prayagraj", "Howrah",
            "Gwalior", "Jabalpur", "Coimbatore", "Vijayawada", "Chandigarh",
            "Mysore", "Ranchi", "Hubli-Dharwad", "Raipur", "Salem",
            "Aligarh", "Bareilly", "Mangalore", "Guntur", "Noida"};
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    View progressOverlay;;


    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        autoCompleteTextView = findViewById(R.id.autoComplete);
        btnLocation = findViewById(R.id.btnLocation);

        progressBar = findViewById(R.id.progressBar);
        progressOverlay = findViewById(R.id.progressOverlay);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        autoCompleteTextView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Users");

        progressBar.setVisibility(View.GONE);
        progressOverlay.setVisibility(View.GONE);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLocation = autoCompleteTextView.getText().toString();

                if(selectedLocation.isEmpty()){
                    Toast.makeText(Choose_Location_Activity.this, "Please Select a Location", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    String uid = user.getUid();

                    usersRef.child(uid).child("Location").setValue(selectedLocation)
                            .addOnSuccessListener(aVoid ->{

                                progressBar.setVisibility(View.VISIBLE);
                                progressOverlay.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(Choose_Location_Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {

                                Toast.makeText(Choose_Location_Activity.this, "Failed to save location", Toast.LENGTH_SHORT).show();
                            });
                }else{
                    Toast.makeText(Choose_Location_Activity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}