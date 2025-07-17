package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jspecify.annotations.Nullable;

public class Login_Activity extends AppCompatActivity {

    private EditText loginWithEmailOrPhone, loginPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private TextView doNotHaveAccount;
    ProgressBar progressBar;
    View progressOverlay;
    AppCompatButton loginWithGoogle;


    @SuppressLint("MissingInflatedId")
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
        progressBar = findViewById(R.id.progressBar);
        progressOverlay = findViewById(R.id.progressOverlay);
        loginWithGoogle = findViewById(R.id.loginWithGoogle);

        progressBar.setVisibility(View.GONE);
        progressOverlay.setVisibility(View.GONE);


        // Login button click
        btnLogin.setOnClickListener(view -> loginUser());

        // "Don't have an account?" click
        doNotHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(Login_Activity.this, Signup_Activity.class));
            finish();
        });


        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                       .requestIdToken(getString(R.string.default_web_client_id))
                       .requestEmail()
                       .build();

               GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(v.getContext(),gso);
               Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                ((Activity) v.getContext()).startActivityForResult(signInIntent, 100);
            }
        });

    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task1) {
                                if (task1.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Firebase Sign-in failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Google Sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        progressOverlay.setVisibility(View.VISIBLE);

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
                        startActivity(new Intent(Login_Activity.this, MainActivity.class));
                        finish();
                    } else {
                        // Login failed
                        Toast.makeText(Login_Activity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }



}
