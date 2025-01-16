package com.byteMe.customer;

import com.byteMe.models.MenuItem;
import com.byteMe.models.Order;
import com.byteMe.models.User;
import java.io.*;
import java.util.*;
import static com.byteMe.Main.scanner;

public class Customer extends User {
    private boolean isVip;
    private List<MenuItem> menu;
    private Map<MenuItem, Integer> cart = new HashMap<>();
    private List<Order> orderHistory = new ArrayList<>();
    private final String CART_FILE = "user_" + getEmail() + "_cart.txt";


    public Customer(String email, String password, String name, String phoneNumber, String address, String gender, boolean isVip) {
        super(email, password, name, phoneNumber, address, gender,false);
        this.isVip = isVip;
        this.menu = new ArrayList<>();
        this.cart = new HashMap<>();
        loadCartFromFile();
    }

    @Override
    public void showMenu() {
        System.out.println("Customer Menu:");
        System.out.println("1. Browse Menu");
        System.out.println("2. Add items to cart");
        System.out.println("3. Modify item quantity in cart");
        System.out.println("4. Remove item from cart");
        System.out.println("5. View cart total");
        System.out.println("6. Place order");
        System.out.println("7. Track order");
        System.out.println("8. View order history");
        System.out.println("9. Cancel order");
        System.out.println("10. Become a VIP");
        System.out.println("11. Leave item review");
        System.out.println("12. View item reviews");
        System.out.println("13. Log out");
    }

    public void addItemToCart(MenuItem item, int quantity) {
        item.setQuantity(item.getQuantity() + quantity);
        cart.put(item, item.getQuantity());
        System.out.println(quantity + " " + item.getName() + "(s) added to cart.");
        saveCartToFile();
    }

    public void modifyCartQuantity(MenuItem item, int newQuantity) {
        if (cart.containsKey(item)) {
            cart.put(item, newQuantity);
            saveCartToFile();
            System.out.println("Updated quantity for " + item.getName() + " to " + newQuantity);
        } else {
            System.out.println("Item not in cart.");
        }
    }

    public void removeItemFromCart(MenuItem item) {
        if (cart.remove(item) != null) {
            saveCartToFile();
            System.out.println(item.getName() + " removed from cart.");
        } else {
            System.out.println("Item not in cart.");
        }
    }

    public double viewCartTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cart.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        System.out.println("Cart total: $" + total);
        return total;
    }

    private void saveCartToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE))) {
            // Write current items in the cart to the file
            for (Map.Entry<MenuItem, Integer> entry : cart.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                writer.write(item.getName() + "," + item.getPrice() + "," + quantity + "," + item.getCategory() + "," + item.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving cart to file: " + e.getMessage());
        }
    }

    private void loadCartFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    String category = parts[3];
                    boolean available = Boolean.parseBoolean(parts[3]);

                    MenuItem item = new MenuItem(name, price, category, available);
                    cart.put(item, quantity);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.err.println("Error loading cart from file: " + e.getMessage());
        }
    }
    public static void handleCheckout(Customer customer) {
        if (customer.cart.isEmpty()) {
            System.out.println("Your cart is empty. Add items before checking out.");
            return;
        }

        double total = customer.viewCartTotal();
        System.out.println("Enter payment details:");
        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Delivery Address: ");
        String address = scanner.nextLine();

        Order order = new Order(customer.getEmail(), new ArrayList<>(customer.cart.keySet()), customer.isVip);
        order.setStatus("Order received");

        customer.orderHistory.add(order);
        System.out.println("Order placed successfully! Total: $" + total);
        customer.clearCart();
        System.out.println("Returning to Customer Menu...");
    }

    public void clearCart() {
        cart.clear();
        System.out.println("Cart cleared after checkout.");
    }

    public void trackOrder(int orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId() == orderId) {
                System.out.println("Order Status: " + order.getStatus());
                return;
            }
        }
        System.out.println("Order not found.");
    }

    public void cancelOrder(int orderId) {
        Set<String> cancellableStatuses = new HashSet<>(Arrays.asList("Order received", "Processing"));

        for (Order order : orderHistory) {
            if (order.getOrderId() == orderId) {
                if (cancellableStatuses.contains(order.getStatus())) {
                    order.setStatus("Cancelled");
                    System.out.println("Order cancelled successfully.");
                } else {
                    System.out.println("Order cannot be cancelled at this stage.");
                }
                return;
            }
        }
        System.out.println("Order not found.");
    }
    public void viewOrderHistory() {
        System.out.println("Order History:");
        for (Order order : orderHistory) {
            System.out.println(order);
        }
    }

    public void leaveReview(MenuItem item, String reviewText) {
        item.addReview(reviewText);
        System.out.println("Review added for " + item.getName());
    }

    public void viewItemReviews(MenuItem item) {
        System.out.println("Reviews for " + item.getName() + ":");
        for (String review : item.getReviews()) { // Assumes MenuItem has getReviews method
            System.out.println("- " + review);
        }
    }

    public void becomeVip(double amount) {
        if (!isVip && amount >= 100) {
            isVip = true;
            System.out.println("Congratulations! You are now a VIP.");
        } else if (isVip) {
            System.out.println("You are already a VIP.");
        } else {
            System.out.println("Insufficient amount. VIP membership requires at least $100.");
        }
    }

    public void setMenu(List<MenuItem> menuItems) {
        this.menu=menuItems;
    }
}
