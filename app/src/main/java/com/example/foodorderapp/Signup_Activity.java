package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.Model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {

    EditText signupName, signupWithEmailOrPhone, signupPassword;
    Button btnSignup;
    TextView alreadyHaveAnAccount;

    FirebaseAuth mAuth;

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
        String name = signupName.getText().toString().trim();
        String email = signupWithEmailOrPhone.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

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

        // Firebase Auth: Create User
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Store user info in Realtime Database
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(new User(name, email))
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(Signup_Activity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        // Redirect to login or home
                                        startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Signup_Activity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(Signup_Activity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
