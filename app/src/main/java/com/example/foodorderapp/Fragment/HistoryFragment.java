package com.example.foodorderapp.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapp.Model.BuyAgainModel;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.BuyAgainAdapter;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuyAgainAdapter adapter;
    private List<BuyAgainModel> foodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // Sample data
        foodList = new ArrayList<>();
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"pizza", "&7"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"burget", "&9"));
        foodList.add(new BuyAgainModel(R.drawable.food_three,"cheese", "&5"));

        adapter = new BuyAgainAdapter(getContext(), foodList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
