package com.example.foodorderapp.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.foodorderapp.Login_Activity;
import com.example.foodorderapp.R;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private AppCompatButton btnSaveInformation;
    private ImageView logout;
    private EditText userName, userLocation, userEmail;
    private FirebaseAuth auth;
    private View progressOverLay;
    private ProgressBar progressBar;




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

        auth = FirebaseAuth.getInstance();

        logout.setOnClickListener(v ->showLogoutDialog());

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
}