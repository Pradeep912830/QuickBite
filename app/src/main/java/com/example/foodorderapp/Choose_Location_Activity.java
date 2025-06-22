package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Choose_Location_Activity extends AppCompatActivity {
    AutoCompleteTextView autoComplete;
    AppCompatButton btnLocation;


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

        autoComplete = findViewById(R.id.autoComplete);
        btnLocation = findViewById(R.id.btnLocation);

        ArrayList<String> cityList = new ArrayList<>();

        cityList.add("Bihar");
        cityList.add("Madhya Pradesh");
        cityList.add("Jharkhand");
        cityList.add("Kolkata");
        cityList.add("Utter Pradesh");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, cityList);
        autoComplete.setAdapter(arrayAdapter);


        autoComplete.setFocusable(false);
        autoComplete.setClickable(true); // still needed for the icon to be clickable
        autoComplete.setKeyListener(null);



        autoComplete.setOnTouchListener((v, event) -> {
            // Show dropdown only when the icon is clicked
            // Assuming the end drawable is on the right (arrow icon)
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (autoComplete.getRight() -
                        autoComplete.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    autoComplete.showDropDown(); // show dropdown
                    return true;
                }
            }
            return false;
        });


        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}