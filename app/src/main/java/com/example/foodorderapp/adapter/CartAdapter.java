package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Model.CartItem;
import com.example.foodorderapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;
    private String userId;

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_view, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);

        holder.foodName.setText(item.getName());
        holder.foodPrice.setText(""+item.getPrice());
        Glide.with(context).load(item.getImageUrl()).into(holder.foodImage);

        holder.foodQuantity.setText(String.valueOf(item.getQuantity()));

        // Increase quantity (UI only)
        holder.buttonIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.foodQuantity.setText(String.valueOf(item.getQuantity()));
        });

        // Decrease quantity (UI only)
        holder.buttonDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.foodQuantity.setText(String.valueOf(item.getQuantity()));
            }
        });

        // Delete item (remove from Firebase)
        holder.buttonDelete.setOnClickListener(v -> {
            if(item.getId() != null){
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(userId)
                        .child("cartItems")
                        .child(item.getId()); // use unique id

                ref.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int currentPos = holder.getAdapterPosition();
                        if(currentPos != RecyclerView.NO_POSITION){
                            cartItemList.remove(currentPos);
                           notifyItemRemoved(currentPos);
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice, foodQuantity;
        ImageButton buttonIncrease, buttonDecrease, buttonDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodQuantity = itemView.findViewById(R.id.food_quantity);
            buttonIncrease = itemView.findViewById(R.id.button_increase);
            buttonDecrease = itemView.findViewById(R.id.button_decrease);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
