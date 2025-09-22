package com.example.foodorderapp.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodorderapp.Model.BuyAgainModel;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.BuyAgainAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuyAgainAdapter adapter;
    private List<BuyAgainModel> foodList;
    private DatabaseReference reference;

    public HistoryFragment(){

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        foodList = new ArrayList<>();
        adapter = new BuyAgainAdapter(getContext(),foodList);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Orders");

        fetchItems();

        return view;
    }

    private void fetchItems(){
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                    foodList.clear();
                    for(DataSnapshot userOrder : snapshot.getChildren()){
                         for(DataSnapshot order : userOrder.getChildren()){
                             for(DataSnapshot itemOrder : order.child("item").getChildren()){
                                 String name = itemOrder.child("name").getValue(String.class);
                                 Object pObj = itemOrder.child("price").getValue();
                                 String price = pObj != null ? String.valueOf(pObj) : null;
                                 String imageUrl = itemOrder.child("imageUrl").getValue(String.class);

                                 if(name != null && price != null && imageUrl != null){
                                     foodList.add(new BuyAgainModel(imageUrl, name, price));
                                 }
                             }
                         }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
