package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Model.PopularModel;
import com.example.foodorderapp.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private List<PopularModel> popularModel;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PopularModel item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopularAdapter(List<PopularModel> popularModel) {
        this.popularModel = popularModel;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        PopularModel item = popularModel.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.ingredients.setText(item.getIngredients());
        holder.description.setText(item.getDescription());

        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .into(holder.imageUrl);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularModel.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUrl;

        TextView name, price, ingredients, description;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            price = itemView.findViewById(R.id.food_price);
            ingredients = itemView.findViewById(R.id.ingredient);
            description = itemView.findViewById(R.id.description);
            imageUrl = itemView.findViewById(R.id.food_image);

        }
    }
}
