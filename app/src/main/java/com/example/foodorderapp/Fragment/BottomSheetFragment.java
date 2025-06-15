package com.example.foodorderapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderapp.DetailsActivity;
import com.example.foodorderapp.Model.MenuItemModel;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.MenuAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom__sheet_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MenuItemModel> menuList = new ArrayList<>();
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        menuList.add(new MenuItemModel("Herbal Pancake", "$7", R.drawable.food_three));
        menuList.add(new MenuItemModel("Salad Bowl", "$5", R.drawable.food_two));
        menuList.add(new MenuItemModel("Fruit Shake", "$6", R.drawable.food_one));
        // Add more items here

        MenuAdapter adapter = new MenuAdapter(menuList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("foodName", item.getName());
            intent.putExtra("foodImage", item.getImageResId());
            startActivity(intent);
        });

        return view;
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