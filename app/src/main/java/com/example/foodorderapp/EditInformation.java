// EditInformation.java
package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

public class EditInformation extends AppCompatActivity {

    ImageView goBack;
    private AppCompatButton editInformationButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        editInformationButton = findViewById(R.id.editInformationButton);
        goBack = findViewById(R.id.goBack);

        editInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CongratsActivity.class);
                startActivity(intent);
            }
        });


        goBack.setOnClickListener(v -> onBackPressed());
    }
}
