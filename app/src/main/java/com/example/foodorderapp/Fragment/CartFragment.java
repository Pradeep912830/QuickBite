package com.example.foodorderapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodorderapp.EditInformation;
import com.example.foodorderapp.Model.CartItem;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartItemList;
    DatabaseReference reference;
    FirebaseAuth auth;
    AppCompatButton button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false); // your fragment layout
        recyclerView = view.findViewById(R.id.recyclerView_cart_item);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditInformation.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        // Sample data
        cartItemList = new ArrayList<>();
        adapter = new CartAdapter(getContext() ,cartItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        loadCartItems();
        return view;

    }

    private void loadCartItems(){
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if(userId == null){
            Toast.makeText(getContext(), "user not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        reference.child(userId).child("cartItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItemList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartItem item = dataSnapshot.getValue(CartItem.class);
                    if(item != null){
                        cartItemList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Fialed:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
