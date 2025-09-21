package com.example.foodorderapp.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodorderapp.Login_Activity;
import com.example.foodorderapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {

    private AppCompatButton btnSaveInformation;
    private ImageView logout, editProfile;
    private EditText userName, userLocation, userEmail;
    private FirebaseAuth auth;
    private View progressOverLay;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private String uid;




    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSaveInformation = view.findViewById(R.id.btnSaveInformation);
        logout = view.findViewById(R.id.logout);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userLocation = view.findViewById(R.id.userLocation);
        progressOverLay = view.findViewById(R.id.progressOverlay);
        progressBar = view.findViewById(R.id.progressBar);
        editProfile = view.findViewById(R.id.editProfile);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);


        logout.setOnClickListener(v ->showLogoutDialog());

        setEditable(false);
        fetchUserData();

        editProfile.setOnClickListener(v ->{
            showProgress(true);
            new Handler().postDelayed(() ->{
                setEditable(true);
                showProgress(false);
                Toast.makeText(getContext(), "You can edit now!", Toast.LENGTH_SHORT).show();
            }, 2000);
        });

        btnSaveInformation.setOnClickListener(v->{
            showProgress(true);
            new Handler().postDelayed(this::saveUserData, 2000);
        });

        return view;
    }

    private void showLogoutDialog(){
        new AlertDialog.Builder(getContext()).setTitle("Logout")
                .setMessage("Are you sure you want to logout?").
                setCancelable(false)
                .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                .setPositiveButton("Delete", ((dialog, which) -> performLogout())).show();
    }

    private void performLogout(){
        progressOverLay.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        new android.os.Handler().postDelayed(() -> {
            // Firebase logout
            auth.signOut();

            // Hide progress overlay
            progressOverLay.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            // Redirect to LoginActivity
            Intent intent = new Intent(getActivity(), Login_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            // Finish current activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        }, 2000); // 2000 ms = 2 sec
    }


    private void fetchUserData() {
        reference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String location = snapshot.child("Location").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    userName.setText(name);
                    userEmail.setText(email);
                    userLocation.setText(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showProgress(false);
                Toast.makeText(getContext(), "Failed to fetch the data!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveUserData(){
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String Location = userLocation.getText().toString().trim();

        if(name.isEmpty()){
            userName.setError("Fill User Name");
            showProgress(false);
        } else if (email.isEmpty()) {
            userEmail.setError("Fill User Email");
            showProgress(false);
        } else if (Location.isEmpty()) {
            userLocation.setError("Fill User Location");
            showProgress(false);
        }else {
            reference.child("name").setValue(name);
            reference.child("Location").setValue(Location);
            reference.child("email").setValue(email).addOnCompleteListener(task -> {
                showProgress(false);
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
                    setEditable(false);
                }else {
                    Toast.makeText(getContext(), "Failed to Save!", Toast.LENGTH_SHORT).show();
                }
            });

            showProgress(false);
        }
    }

    private void setEditable(boolean enabled){
        userName.setEnabled(enabled);
        userLocation.setEnabled(enabled);
        userEmail.setEnabled(enabled);
    }

    private void showProgress(boolean show){
        progressOverLay.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}