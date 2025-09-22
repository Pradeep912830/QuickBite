package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Model.BuyAgainModel;
import com.example.foodorderapp.R;



import java.util.List;

public class BuyAgainAdapter extends RecyclerView.Adapter<BuyAgainAdapter.ViewHolder> {

    private Context context;
    private List<BuyAgainModel> itemList;

    public BuyAgainAdapter(Context context, List<BuyAgainModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuyAgainModel item = itemList.get(position);
        holder.foodName.setText(item.getName());
        holder.foodPrice.setText(item.getPrice());
        Glide.with(context).load(item.getImageUrl()).into(holder.foodImage);

        // Handle button click if needed
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.history_foodImage);
            foodName = itemView.findViewById(R.id.history_foodName);
            foodPrice = itemView.findViewById(R.id.history_foodPrice);
        }
    }
}
