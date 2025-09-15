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

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    Context context;

    public CartAdapter(Context context ,List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_view, parent, false); // your custom layout file
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);

        holder.foodName.setText(item.getName());
        holder.foodPrice.setText(item.getPrice());

        Glide.with(context).load(item.getImageUrl()).into(holder.foodImage);



//        holder.foodQuantity.setText(String.valueOf(item.getQuantity()));
//        holder.buttonIncrease.setOnClickListener(v -> {
//            item.setQuantity(item.getQuantity() + 1);
//            notifyItemChanged(position);
//        });
//
//        holder.buttonDecrease.setOnClickListener(v -> {
//            if (item.getQuantity() > 1) {
//                item.setQuantity(item.getQuantity() - 1);
//                notifyItemChanged(position);
//            }
//        });

//        holder.buttonDelete.setOnClickListener(v -> {
//            cartItems.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, cartItems.size());
//        });



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


//            foodQuantity = itemView.findViewById(R.id.food_quantity);
//            buttonIncrease = itemView.findViewById(R.id.button_increase);
//            buttonDecrease = itemView.findViewById(R.id.button_decrease);
//            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
