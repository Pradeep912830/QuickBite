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
        View view = LayoutInflater.from(context).inflate(R.layout.buy_again_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuyAgainModel item = itemList.get(position);
        holder.nameText.setText(item.getName());
        holder.priceText.setText(item.getPrice());
        holder.imageView.setImageResource(item.getImage());

        // Handle button click if needed
        holder.buyAgainButton.setOnClickListener(v -> {
            Toast.makeText(context, "Buying again: " + item.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText, priceText;
        Button buyAgainButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.by_again_image);
            nameText = itemView.findViewById(R.id.buy_again_foodName);
            priceText = itemView.findViewById(R.id.buy_again_foodPrice);
            buyAgainButton = itemView.findViewById(R.id.buy_again_button);
        }
    }
}
