package com.example.foodorderapp;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.foodorderapp.Model.NotificationItem;
import com.example.foodorderapp.adapter.NotificationAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Notification_Buttom_Fragment extends BottomSheetDialogFragment {

    public Notification_Buttom_Fragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification__buttom_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.notificationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<NotificationItem> notifications = new ArrayList<>();
        notifications.add(new NotificationItem("Your order has been Canceled Successfully", R.drawable.sad));
        notifications.add(new NotificationItem("Order has been taken by the driver", R.drawable.truck));
        notifications.add(new NotificationItem("Congrats Your Order Placed", R.drawable.success));

        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        view.post(() -> {
            View parent = (View) view.getParent();
            if (parent != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight((int)(getResources().getDisplayMetrics().heightPixels * 0.5)); // Half-screen
            }
        });

        return view;
    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

                // Optional: Set to expanded state and fixed height
                bottomSheet.getLayoutParams().height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.75); // 75% height
                bottomSheet.setLayoutParams(bottomSheet.getLayoutParams());
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

}
