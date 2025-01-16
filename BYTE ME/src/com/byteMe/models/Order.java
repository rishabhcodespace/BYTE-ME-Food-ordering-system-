package com.byteMe.models;

import java.io.*;
import java.util.*;

public class Order {
    private static int orderCounter = 0;
    private int orderId;
    private List<MenuItem> items;
    private boolean isVip;
    private String status;
    private String userEmail;

    // Static queue to manage all orders
    private static Queue<Order> orderQueue = new PriorityQueue<>((o1, o2) -> Boolean.compare(o2.isVip(), o1.isVip()));

    public Order(String userEmail, List<MenuItem> items, boolean isVip) {
        for (MenuItem item : items) {
            if (!item.isAvailable()) {
                throw new IllegalArgumentException("Item " + item.getName() + " is out of stock and cannot be ordered.");
            }
        }

        this.orderId = ++orderCounter;
        this.userEmail = userEmail; // Track the user's email ID
        this.items = items;
        this.isVip = isVip;
        this.status = "Order received";
        orderQueue.add(this);

        saveOrderHistory();
    }

    public int getOrderId() { return orderId; }
    public List<MenuItem> getItems() { return items; }
    public boolean isVip() { return isVip; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getUserEmail() { return userEmail; }
    public static Queue<Order> getOrderQueue() { return orderQueue; }

    public static Order findOrderById(int orderId) {
        for (Order order : orderQueue) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public static List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orderQueue) {
            if ("Pending".equalsIgnoreCase(order.getStatus())) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    private void saveOrderHistory() {
        String fileName = "user_" + userEmail + "_orders.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(toString());
            writer.newLine();
            System.out.println("Order saved to history for user: " + userEmail);
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }

    public static void loadOrderHistory(String userEmail) {
        String fileName = "user_" + userEmail + "_orders.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("Order History for User: " + userEmail);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No order history found for user: " + userEmail);
        } catch (IOException e) {
            System.out.println("Error reading order history: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder();
        for (MenuItem item : items) {
            itemsString.append(item.getName()).append(" (x").append(item.getQuantity()).append(") - $").append(item.getPrice()*item.getQuantity()).append(", ");
        }
        return "Order{" +
                "orderId=" + orderId +
                ", items=[" + itemsString + "]" +
                ", isVip=" + isVip +
                ", status='" + status + '\'' +
                '}';
    }
}
