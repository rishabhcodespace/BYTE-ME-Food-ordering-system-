package com.byteMe.admin;

import com.byteMe.models.MenuItem;
import com.byteMe.models.Order;
import com.byteMe.models.PendingOrdersPage;
import com.byteMe.models.User;
import java.util.*;

public class Admin extends User {
    private Queue<Order> orderQueue = new PriorityQueue<>((o1, o2) -> Boolean.compare(o2.isVip(), o1.isVip()));
    private TreeSet<String> itemCategories = new TreeSet<>();
    private TreeMap<String, Integer> dailySales = new TreeMap<>();
    private List<MenuItem> menuItems;
    private double totalSales = 0;
    private Set<Integer> processedOrderIds = new HashSet<>();

    public Admin(String email, String password, String name, String phoneNumber, String address, String gender, List<MenuItem> menuItems) {
        super(email, password, name, phoneNumber, address, gender, false);
        this.menuItems = menuItems;
    }

    @Override
    public void showMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Add new items");
        System.out.println("2. Update existing items");
        System.out.println("3. Remove items");
        System.out.println("4. View pending orders");
        System.out.println("5. Update order status");
        System.out.println("6. Generate daily sales report");
        System.out.println("7. Process refunds");
        System.out.println("8. Log out");
    }

    public void addNewItem(String name, double price, String category, boolean available) {
        MenuItem item = new MenuItem(name, price, category, available);
        menuItems.add(item);
        itemCategories.add(category);
        System.out.println("Item added: " + item);
    }

    public void updateItem(String name, double newPrice, boolean availability) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setPrice(newPrice);
                item.setAvailable(availability);
                System.out.println("Item updated: " + item);
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public void removeItem(String name) {
        menuItems.removeIf(item -> item.getName().equalsIgnoreCase(name));
        updatePendingOrders(name);
        System.out.println("Item removed: " + name);
    }

    private void updatePendingOrders(String itemName) {
        for (Order order : orderQueue) {
            if (order.toString().contains(itemName)) {
                order.setStatus("Denied");
            }
        }
    }

    public void viewPendingOrders() {
        System.out.println("Pending Orders:");
        for (Order order : Order.getOrderQueue()) {
            System.out.println(order);
        }
        List<Order> pendingOrders = Order.getPendingOrders();
        new PendingOrdersPage(pendingOrders);
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        Order order = Order.findOrderById(orderId);
        if (order != null) {
            List<String> completedStatuses = Arrays.asList("Delivered", "Completed", "Out for Delivery", "Order Received");

            if (completedStatuses.contains(newStatus) && !completedStatuses.contains(order.getStatus())) {
                updateTotalSales(order);
                processedOrderIds.add(orderId);
            }
            order.setStatus(newStatus);
            System.out.println("Order updated: " + order);
        } else {
            System.out.println("Order not found.");
        }
    }

    private void updateTotalSales(Order order) {
        for (MenuItem item : order.getItems()) {
            totalSales += item.getPrice()*item.getQuantity();
            dailySales.put(item.getName(), dailySales.getOrDefault(item.getName(), item.getQuantity()));
        }
    }

    public void processRefund(int orderId) {
        for (Order order :Order.getOrderQueue()) {
            if (order.getOrderId() == orderId) {
                System.out.println("Refund processed for Order ID: " + orderId);
                if (order.getStatus().equalsIgnoreCase("Cancelled")){
                System.out.println("successfully Refunded ");
                order.setStatus("Refunded");
                return;}
                else{ System.out.println(" can not refund at this stage ");}
            }
            else{System.out.println("Order not found.");}
        }
    }

    public void generateDailySalesReport() {
        List<String> validStatuses = Arrays.asList("Delivered", "Completed", "Out for Delivery", "Order received");

        int totalOrders = (int) orderQueue.stream()
                .filter(order -> validStatuses.contains(order.getStatus()))
                .count();

        System.out.println("\nDaily Sales Report:");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders: " + processedOrderIds.size());
        System.out.println("Most Popular Items: " + dailySales);
    }
}
