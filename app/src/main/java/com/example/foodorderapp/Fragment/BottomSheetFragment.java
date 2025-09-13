package com.example.foodorderapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodorderapp.DetailsActivity;
import com.example.foodorderapp.Model.MenuItemModel;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.MenuAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    ImageView backButton;
    RecyclerView recyclerView;
    MenuAdapter adapter;
    List<MenuItemModel> menuList;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom__sheet_, container, false);

        recyclerView = view.findViewById(R.id.menuRecyclerView);
        backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> getDialog().onBackPressed());

  

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        menuList = new ArrayList<>();
        adapter = new MenuAdapter(menuList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("foodName", item.getName());
            intent.putExtra("foodPrice", item.getPrice());
            intent.putExtra("foodImageUrl", item.getImageUrl());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("ingredients", item.getIngredients());

            startActivity(intent);
        });


        fetchMenuItems();
        return view;
    }

    private void fetchMenuItems(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FoodItems");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue(String.class);
                    Object pobj = dataSnapshot.child("price").getValue();
                    String price = pobj != null ? String.valueOf(pobj) : null;
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String ingredients = dataSnapshot.child("ingredients").getValue(String.class);

                    if(name != null && price != null && imageUrl != null && description != null || ingredients != null){
                        menuList.add(new MenuItemModel(name, price, imageUrl, description, ingredients));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            ViewGroup.LayoutParams params = parent.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            parent.setLayoutParams(params);

            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setSkipCollapsed(true);
        }

    }

}