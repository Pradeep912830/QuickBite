package com.example.foodorderapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DetailsActivity;
import com.example.foodorderapp.Model.FoodItem;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.FoodAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private FoodAdapter adapter;
    private List<FoodItem> foodList;

    private DatabaseReference foodRef;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodList = new ArrayList<>();
        adapter = new FoodAdapter(getContext(), foodList);
        recyclerView.setAdapter(adapter);

        // 🔹 Firebase reference
        foodRef = FirebaseDatabase.getInstance().getReference("FoodItems");

        loadFoodItems();


        adapter.setOnItemClickListener(v ->{
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("price", v.getPrice());
            intent.putExtra("name", v.getName());
            intent.putExtra("imageUrl", v.getImageUrl());
            startActivity(intent);
        });

        // 🔎 Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        return view;
    }

    private void loadFoodItems() {
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodItem item = dataSnapshot.getValue(FoodItem.class);
                    if (item != null) {
                        foodList.add(item);
                    }
                }
                adapter.updateList(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
