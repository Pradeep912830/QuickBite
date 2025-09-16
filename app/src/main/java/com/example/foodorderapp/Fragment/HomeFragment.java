package com.example.foodorderapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodorderapp.DetailsActivity;
import com.example.foodorderapp.Model.PopularModel;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.PopularAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private RecyclerView recyclerView;
    private List<PopularModel> popularList;
    private PopularAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);
        recyclerView = view.findViewById(R.id.recyclerView);

        TextView menuItemBtn = view.findViewById(R.id.menu_button);
        menuItemBtn.setOnClickListener(v -> {
            BottomSheetFragment bottomSheet = new BottomSheetFragment();
            bottomSheet.show(getParentFragmentManager(), "MenuBottomSheet");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                popularList = new ArrayList<>();
                adapter = new PopularAdapter(popularList);
                recyclerView.setAdapter(adapter);

      adapter.setOnItemClickListener(v -> {
          Intent intent = new Intent(getActivity(), DetailsActivity.class);
          intent.putExtra("name", v.getName());
          intent.putExtra("price", v.getPrice());
          intent.putExtra("ingredients", v.getIngredients());
          intent.putExtra("imageUrl", v.getImageUrl());
          intent.putExtra("description", v.getDescription());
          startActivity(intent);
      });


        setupImageSlider();
        fetchPopularItems();

        return view;
    }

    private void setupImageSlider() {
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.food_one, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_two, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_three, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_four, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_one, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_two, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_three, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.food_four, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Toast.makeText(requireContext(), "Item clicked: " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doubleClick(int i) {
                Toast.makeText(requireContext(), "Item double-clicked: " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPopularItems() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FoodItems");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue(String.class);
                    Object pobj = dataSnapshot.child("price").getValue();
                    String price = pobj != null ? String.valueOf(pobj) : null;
                    String ingredients = dataSnapshot.child("ingredients").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);

                    if (name != null && price != null && ingredients != null && imageUrl != null && description != null) {
                        popularList.add(new PopularModel(name, price, ingredients, imageUrl, description));
                    }
                }
                Collections.shuffle(popularList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Fetch data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
