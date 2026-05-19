package com.medilux.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderId;
    private int userId;
    private String items;
    private String address;
    private double total;
    private String status;
    private String orderDate;

    public Order() {}

    public Order(int id, String orderId, int userId, String items, String address, double total, String status, String orderDate) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
