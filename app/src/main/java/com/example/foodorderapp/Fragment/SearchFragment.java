package com.example.foodorderapp.Fragment;
import com.example.foodorderapp.DetailsActivity;
import com.example.foodorderapp.adapter.FoodAdapter;
import com.example.foodorderapp.Model.FoodItem;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.example.foodorderapp.R;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private FoodAdapter adapter;
    private List<FoodItem> foodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search_searchView);
        recyclerView = view.findViewById(R.id.search_recyclerView);

        foodList = getFoodItems();
        adapter = new FoodAdapter(getContext(), foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("food_name", item.getName());
            intent.putExtra("food_image", item.getImageResId());
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        return view;
    }

    private List<FoodItem> getFoodItems() {
        List<FoodItem> list = new ArrayList<>();
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        list.add(new FoodItem("Herbal Pancake", "$7", R.drawable.food_three));
        list.add(new FoodItem("Chicken Soup", "$5", R.drawable.food_two));
        list.add(new FoodItem("Vegetable Salad", "$6", R.drawable.food_one));
        list.add(new FoodItem("Cheese Burger", "$8", R.drawable.food_four));
        // Add more items as needed
        return list;
    }
}
