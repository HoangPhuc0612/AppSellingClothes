package com.example.doanfashionapp.DTO;

public class Order {
    private String orderId;
    private String username;
    private String orderDate;
    private int totalPrice;

    public Order(String orderId, String username, String orderDate, int totalPrice) {
        this.orderId = orderId;
        this.username = username;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}



