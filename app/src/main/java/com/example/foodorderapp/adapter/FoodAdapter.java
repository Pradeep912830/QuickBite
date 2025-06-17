package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.foodorderapp.Model.FoodItem;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.Model.MenuItemModel;
import com.example.foodorderapp.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItem> foodList;
    private List<FoodItem> fullList;


    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FoodItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = new ArrayList<>(foodList);
        this.fullList = new ArrayList<>(foodList);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_items, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.image.setImageResource(item.getImageResId());


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void filter(String text) {
        foodList.clear();
        if (text.isEmpty()) {
            foodList.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (FoodItem item : fullList) {
                if (item.getName().toLowerCase().contains(text)) {
                    foodList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public FoodViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.food_image);
            name = itemView.findViewById(R.id.food_name);
            price = itemView.findViewById(R.id.food_price);
        }
    }
}
