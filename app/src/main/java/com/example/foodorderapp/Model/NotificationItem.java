package com.example.foodorderapp.Model;

public class NotificationItem {
    private final String message;
    private final int iconResId;

    public NotificationItem(String message, int iconResId) {
        this.message = message;
        this.iconResId = iconResId;
    }

    public String getMessage() {
        return message;
    }

    public int getIconResId() {
        return iconResId;
    }
}
