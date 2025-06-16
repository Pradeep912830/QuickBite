package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.Model.MenuItemModel;
import com.example.foodorderapp.Model.PopularModel;
import com.example.foodorderapp.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private List<PopularModel> itemList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PopularModel item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopularAdapter(List<PopularModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        PopularModel item = itemList.get(position);
        holder.food_image.setImageResource(item.getImageResId());
        holder.food_name.setText(item.getName());
        holder.food_price.setText(item.getPrice());
        holder.food_addtocart.setText(item.getFood_addtocartName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView food_image;

        TextView food_price, food_addtocart, food_name;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.food_image);
            food_name = itemView.findViewById(R.id.food_name);
            food_price = itemView.findViewById(R.id.food_price);
            food_addtocart = itemView.findViewById(R.id.food_addtocart);

        }
    }
}
