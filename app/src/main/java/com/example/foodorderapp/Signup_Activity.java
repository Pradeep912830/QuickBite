package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.Model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {

    EditText signupName, signupWithEmailOrPhone, signupPassword, mobileNumber;
    Button btnSignup;
    TextView alreadyHaveAnAccount;

    ProgressBar progressBar;
    View progressOverlay;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Replace with your actual XML layout name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Hooks
        signupName = findViewById(R.id.signupName);
        signupWithEmailOrPhone = findViewById(R.id.signupWithEmailOrPhone);
        signupPassword = findViewById(R.id.signupPassword);
        btnSignup = findViewById(R.id.btnSignup);
        alreadyHaveAnAccount = findViewById(R.id.alreadyHaveAnAccount);
        mobileNumber = findViewById(R.id.mobileNumber);
        progressBar = findViewById(R.id.progressBar);
        progressOverlay = findViewById(R.id.progressOverlay);

        progressBar.setVisibility(View.GONE);
        progressOverlay.setVisibility(View.GONE);

        // Sign up button logic
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Already have an account
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to login activity
                startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                finish();
            }
        });
    }

    private void registerUser() {

        progressBar.setVisibility(View.VISIBLE);
        progressOverlay.setVisibility(View.VISIBLE);


        String name = signupName.getText().toString().trim();
        String email = signupWithEmailOrPhone.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String phoneNum = mobileNumber.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(name)) {
            signupName.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            signupWithEmailOrPhone.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            signupPassword.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            signupPassword.setError("Password must be >= 6 characters");
            return;
        }

        if(TextUtils.isEmpty(phoneNum)){
            mobileNumber.setError("Enter valid mobile number!");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        User user = new User(name, email, phoneNum);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(uid)
                                .setValue(user)
                                .addOnCompleteListener(dbTask -> {
                                    progressBar.setVisibility(View.GONE);
                                    progressOverlay.setVisibility(View.GONE);

                                    if (dbTask.isSuccessful()) {
                                        startActivity(new Intent(Signup_Activity.this, Choose_Location_Activity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Signup_Activity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        progressOverlay.setVisibility(View.GONE);
                        Toast.makeText(Signup_Activity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



        // Firebase Auth: Create User

    }
}
