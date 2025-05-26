package com.example.foodorderapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapp.Model.CartItem;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false); // your fragment layout
        recyclerView = view.findViewById(R.id.recyclerView_cart_item);

        // Sample data
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));
        cartItems.add(new CartItem("Spacy fresh crab", "$35", R.drawable.food_three, 1));
        cartItems.add(new CartItem("Juicy beef burger", "$28", R.drawable.food_three, 2));

        adapter = new CartAdapter(cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
