package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.foodorderapp.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {

    EditText signupName, signupWithEmailOrPhone, signupPassword, mobileNumber;
    AppCompatButton btnSignup, signupWithGoogle, signupWithFacebook;
    TextView alreadyHaveAnAccount;
    ProgressBar progressBar;
    View progressOverlay;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "GoogleSignUp";



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
        signupWithGoogle = findViewById(R.id.signupWithGoogle);
        signupWithFacebook = findViewById(R.id.signupWithFacebook);

        progressBar = findViewById(R.id.progressBar);
        progressOverlay = findViewById(R.id.progressOverlay);

        gone();

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signupWithGoogle.setOnClickListener(v -> signIn());
    }

    private void signIn(){
        visible();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle!" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Log.w(TAG, "Google sign in failed", e);
                gone();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                   checkIfAccountExists(user);
                }

            }else {
                gone();
                Toast.makeText(Signup_Activity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfAccountExists(FirebaseUser user){
        String email = user.getEmail();
        String name = user.getDisplayName();

        if(email == null){
            Toast.makeText(this, "No email found for this account.", Toast.LENGTH_SHORT).show();
            gone();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                boolean accountExists = !task.getResult().getSignInMethods().isEmpty();
                if(accountExists){
                   Toast.makeText(Signup_Activity.this, "Account already Exist!", Toast.LENGTH_SHORT).show();
                   gone();
                   return;

                }else {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User users = new User(name, email, "");
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(uid)
                            .setValue(users)
                            .addOnCompleteListener(dbTask -> {
                              gone();

                              if (dbTask.isSuccessful()){
                                  Toast.makeText(Signup_Activity.this, "Welcome "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(Signup_Activity.this, Choose_Location_Activity.class);
                                  startActivity(intent);
                                  finish();
                              }else {
                                  Toast.makeText(Signup_Activity.this, "Error in saving user data!", Toast.LENGTH_SHORT).show();
                                  gone();
                              }
                    });
                }

            }else {
                Toast.makeText(Signup_Activity.this, "Error checking account!", Toast.LENGTH_SHORT).show();
                gone();
            }
        });
    }

    private void visible(){
        progressBar.setVisibility(View.VISIBLE);
        progressOverlay.setVisibility(View.VISIBLE);
    }

    private void gone(){
        progressBar.setVisibility(View.GONE);
        progressOverlay.setVisibility(View.GONE);
    }

    private void registerUser() {

        visible();

        String name = signupName.getText().toString().trim();
        String email = signupWithEmailOrPhone.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String phoneNum = mobileNumber.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(name)) {
            signupName.setError("Name is required");
            gone();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            signupWithEmailOrPhone.setError("Email is required");
            gone();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            signupPassword.setError("Password is required");
            gone();
            return;
        }

        if (password.length() < 6) {
            signupPassword.setError("Password must be >= 6 characters");
            gone();
            return;
        }

        if(TextUtils.isEmpty(phoneNum)){
            mobileNumber.setError("Enter valid mobile number!");
            gone();
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
                                    gone();

                                    if (dbTask.isSuccessful()) {
                                        startActivity(new Intent(Signup_Activity.this, Choose_Location_Activity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Signup_Activity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        gone();
                        Toast.makeText(Signup_Activity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void crateAccountWithGoogle(){

    }
}
