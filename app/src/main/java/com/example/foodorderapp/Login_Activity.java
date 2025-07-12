package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Login_Activity extends AppCompatActivity {

    private EditText loginWithEmailOrPhone, loginPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private TextView doNotHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // make sure this matches your XML name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        loginWithEmailOrPhone = findViewById(R.id.loginWithEmailOrPhone);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        doNotHaveAccount = findViewById(R.id.do_notHaveAccount);

        // Login button click
        btnLogin.setOnClickListener(view -> loginUser());

        // "Don't have an account?" click
        doNotHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(Login_Activity.this, Signup_Activity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = loginWithEmailOrPhone.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            loginWithEmailOrPhone.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login_Activity.this, task -> {
                    if (task.isSuccessful()) {
                        // Login success
                        Toast.makeText(Login_Activity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_Activity.this, MainActivity.class));
                        finish();
                    } else {
                        // Login failed
                        Toast.makeText(Login_Activity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
