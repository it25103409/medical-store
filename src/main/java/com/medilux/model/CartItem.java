package com.medilux.model;

/**
 * CartItem Model
 * Cart Part (Part 6) - Represents a single item in the shopping cart.
 * CRUD: Used in cart display (READ) and order placement (CREATE).
 */
public class CartItem {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private String icon;

    public CartItem() {}

    public CartItem(int productId, String name, double price, int quantity, String icon) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.icon = icon;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public double getSubtotal() {
        return this.price * this.quantity;
    }
}
