package com.example.foodorderapp.Model;

import java.util.Map;

public class Order {
    private String orderId;
    private String userId;
    private Map<String, CartItem> item;
    private double totalAmount;
    private int totalQuantity;
    private String status;
    private long timestamp;
    private String name;
    private String address;
    private String phone;

    public Order(){

    }
    public Order(String orderId, String userId,
                 Map<String, CartItem> item, double totalAmount,
                 int totalQuantity, String status, long timestamp,
                 String name, String address, String phone) {

        this.orderId = orderId;
        this.userId = userId;
        this.item = item;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.status = status;
        this.timestamp = timestamp;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, CartItem> getItem() {
        return item;
    }

    public void setItem(Map<String, CartItem> item) {
        this.item = item;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
