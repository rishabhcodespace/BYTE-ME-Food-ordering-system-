package com.byteMe.models;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean available;
    private final List<String> reviews;
    private int quantity;

    public MenuItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.reviews = new ArrayList<>();
        this.quantity =0;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void addReview(String review) {
        reviews.add(review);
    }
    public List<String> getReviews() {
        return new ArrayList<>(reviews);
    }
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "MenuItem{name='" + name + "', price=" + price + ", category='" + category + "', available=" + available + "}";
    }
}
