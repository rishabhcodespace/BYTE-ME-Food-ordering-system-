package com.byteMe;

import com.byteMe.admin.Admin;
import com.byteMe.customer.Customer;
import com.byteMe.models.*;
import com.byteMe.models.MenuItem;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Customer> registeredCustomers = new ArrayList<>();
    private static List<MenuItem> menuItems = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        menuItems.add(new MenuItem("Pizza", 100, "Food", true));
        menuItems.add(new MenuItem("Burger", 40, "Food", true));
        menuItems.add(new MenuItem("Soda", 30, "Beverage", true));
        menuItems.add(new MenuItem("Coffee", 20, "Beverage", true));

        boolean isAppRunning = true;

        while (isAppRunning) {
            System.out.println("Welcome to Byte Me!");
            SwingUtilities.invokeLater(() -> new MainMenu(registeredCustomers,menuItems,orders));
            System.out.println("1. Login as Customer");
            System.out.println("2. Login as Admin");
            System.out.println("3. Signup as Customer");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleCustomerLogin();
                    break;
                case 2:
                    handleAdminLogin();
                    break;
                case 3:
                    handleCustomerSignup();
                    break;
                case 4:
                    System.out.println("Exiting Byte Me...");
                    isAppRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }

    private static void handleCustomerSignup() {
        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        System.out.print("Enter your gender: ");
        String gender = scanner.nextLine();

        System.out.print("Are you a VIP customer? (yes/no): ");
        boolean isVip = scanner.nextLine().equalsIgnoreCase("yes");

        try {
            Customer customer = new Customer(email, password, name, phoneNumber, address, gender, isVip);
            registeredCustomers.add(customer);
            System.out.println("Customer signup successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error during signup: " + e.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private static void handleCustomerLogin() {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            Customer customer = authenticateCustomer(email, password);
            System.out.println("Customer login successful!");
            customer.setMenu(menuItems);
            handleCustomerMenu(customer);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
        }
    }


    private static Customer authenticateCustomer(String email, String password) {
        for (Customer customer : registeredCustomers) {
            if (customer.getEmail().equals(email)) {
                if (customer.getPassword().equals(password)) {
                    return customer; // Login successful
                } else {
                    throw new IllegalArgumentException("Invalid password.");
                }
            }
        }
        throw new IllegalArgumentException("Email not registered.");
    }

    private static void handleAdminLogin() {
        try {
            System.out.print("Enter admin email: ");
            String email = scanner.nextLine();
            System.out.print("Enter admin password: ");
            String password = scanner.nextLine();

            validateAdminCredentials(email, password);

            System.out.println("Admin login successful!");
            Admin admin = new Admin(email, password, "Admin", "", "", "", menuItems);
            handleAdminMenu(admin);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
        }
    }

    private static void validateAdminCredentials(String email, String password) {
        if (!email.equals("admin@byteme")) {
            throw new IllegalArgumentException("Invalid admin email.");
        }
        if (!password.equals("admin1234")) {
            throw new IllegalArgumentException("Invalid admin password.");
        }
    }

    private static void handleAdminMenu(Admin admin) {
        boolean isAdminSessionActive = true;

        while (isAdminSessionActive) {
            admin.showMenu();
            System.out.print("Choose an option: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    // will Call method for adding new items in menu
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter item category: ");
                    String category = scanner.nextLine();
                    System.out.print("Is the item available (true/false): ");
                    boolean available = scanner.nextBoolean();
                    admin.addNewItem(name, price, category, available);
                    break;
                case 2:
                    // will  Call the  method for updating the existing items in menu
                    System.out.print("Enter item name to update: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Is the item available (true/false): ");
                    boolean newAvailability = scanner.nextBoolean();
                    admin.updateItem(updateName, newPrice, newAvailability);
                    break;
                case 3:
                    // Call method to remove items from menu
                    System.out.print("Enter item name to remove: ");
                    String removeName = scanner.nextLine();
                    admin.removeItem(removeName);
                    break;
                case 4:
                    // Call method to view pending orders of the customers
                    admin.viewPendingOrders();
                    break;
                case 5:
                    // Call method to update order status for particular order ID
                    System.out.print("Enter order ID to update: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine();
                    admin.updateOrderStatus(orderId, newStatus);
                    break;
                case 6:
                    // Call method to generate daily sales report
                    admin.generateDailySalesReport();
                    break;
                case 7:
                    //call method for refund
                    System.out.print("Enter order ID to refund : ");
                    int orderId_to_refund = scanner.nextInt();
                    scanner.nextLine();
                    admin.processRefund(orderId_to_refund);
                    break;
                case 8:
                    System.out.println("Logging out... >> logged out successfully ");
                    isAdminSessionActive = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void handleCustomerMenu(Customer customer) {
        boolean isCustomerSessionActive = true;

        while (isCustomerSessionActive) {
            customer.showMenu();
            System.out.print("Choose an option: ");
            int customerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (customerChoice) {
                case 1:
                    browseMenu(customer);
                    break;
                case 2:
                    System.out.print("Enter item name to add to cart: ");
                    String itemName = scanner.nextLine();
                    MenuItem itemToAdd = findMenuItemByName(itemName);
                    if (itemToAdd != null) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        customer.addItemToCart(itemToAdd, quantity);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter item name to modify quantity: ");
                    String modifyItemName = scanner.nextLine();
                    MenuItem itemToModify = findMenuItemByName(modifyItemName);
                    if (itemToModify != null) {
                        System.out.print("Enter new quantity: ");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine();
                        customer.modifyCartQuantity(itemToModify, newQuantity);
                    } else {
                        System.out.println("Item not found in menu.");
                    }
                    break;
                case 4:
                    System.out.print("Enter item name to remove from cart: ");
                    String removeItemName = scanner.nextLine();
                    MenuItem itemToRemove = findMenuItemByName(removeItemName);
                    if (itemToRemove != null) {
                        customer.removeItemFromCart(itemToRemove);
                    } else {
                        System.out.println("Item not found in menu.");
                    }
                    break;
                case 5:
                    customer.viewCartTotal();
                    break;
                case 6:
                    System.out.println("Proceeding to checkout...");
                    Customer.handleCheckout(customer);
                    break;
                case 7:
                    System.out.print("Enter Order ID to track: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    customer.trackOrder(orderId);
                    break;
                case 8:
                    customer.viewOrderHistory();
                    break;
                case 9:
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelOrderId = scanner.nextInt();
                    scanner.nextLine();
                    customer.cancelOrder(cancelOrderId);
                    break;
                case 10:
                    System.out.print("Enter amount for VIP upgrade: ");
                    double amount = scanner.nextDouble();
                    customer.becomeVip(amount);
                    break;
                case 11:
                    System.out.print("Enter item name to leave a review: ");
                    String reviewItemName = scanner.nextLine();
                    MenuItem reviewItem = findMenuItemByName(reviewItemName);
                    if (reviewItem != null) {
                        System.out.print("Enter your review: ");
                        String reviewText = scanner.nextLine();
                        customer.leaveReview(reviewItem, reviewText);
                    } else {
                        System.out.println("Item not found for review.");
                    }
                    break;
                case 12:
                    System.out.print("Enter item name to view reviews: ");
                    String reviewItemToView = scanner.nextLine();
                    MenuItem itemToViewReviews = findMenuItemByName(reviewItemToView);
                    if (itemToViewReviews != null) {
                        customer.viewItemReviews(itemToViewReviews);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 13:
                    System.out.println("Logging out ...... >> log out successfully ");
                    isCustomerSessionActive = false; // Exit customer menu loop
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static MenuItem findMenuItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private static void browseMenu(Customer customer) {
        boolean browsing = true;

        while (browsing) {
            System.out.println("\nBrowse Menu Options:");
            System.out.println("1. View all items");
            System.out.println("2. Filter by category");
            System.out.println("3. Filter by availability");
            System.out.println("4. Sort by price");
            System.out.println("5. Sort by name");
            System.out.println("6. Search by item name");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            List<MenuItem> itemsToDisplay = new ArrayList<>(menuItems);

            switch (choice) {
                case 1:
                    displayMenuItems(itemsToDisplay);
                    // Open the GUI to view all items
                    new MenuDisplayPage(itemsToDisplay); // this will take to menu display page
                    break;
                case 2:
                    System.out.print("Enter category (e.g., Food, Beverage): ");
                    String category = scanner.nextLine();
                    displayMenuItems(filterByCategory(itemsToDisplay, category));
                    break;
                case 3:
                    System.out.print("Enter availability (true for available, false for unavailable): ");
                    boolean availability = scanner.nextBoolean();
                    scanner.nextLine();
                    displayMenuItems(filterByAvailability(itemsToDisplay, availability));
                    break;
                case 4:
                    System.out.print("Sort by price: 1 for Ascending, 2 for Descending: ");
                    int priceSortOption = scanner.nextInt();
                    scanner.nextLine();
                    displayMenuItems(sortByPrice(itemsToDisplay, priceSortOption == 1));
                    break;
                case 5:
                    System.out.print("Sort by name: 1 for Ascending, 2 for Descending: ");
                    int nameSortOption = scanner.nextInt();
                    scanner.nextLine();
                    displayMenuItems(sortByName(itemsToDisplay, nameSortOption == 1));
                    break;
                case 6:
                    System.out.print("Enter item name to search: ");
                    String searchName = scanner.nextLine();
                    displayMenuItems(searchByName(itemsToDisplay, searchName));
                    break;
                case 7:
                    browsing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static List<MenuItem> filterByCategory(List<MenuItem> items, String category) {
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    private static List<MenuItem> filterByAvailability(List<MenuItem> items, boolean availability) {
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.isAvailable() == availability) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    private static List<MenuItem> sortByPrice(List<MenuItem> items, boolean ascending) {
        items.sort((item1, item2) -> ascending
                ? Double.compare(item1.getPrice(), item2.getPrice())
                : Double.compare(item2.getPrice(), item1.getPrice()));
        return items;
    }

    private static List<MenuItem> sortByName(List<MenuItem> items, boolean ascending) {
        items.sort((item1, item2) -> ascending
                ? item1.getName().compareToIgnoreCase(item2.getName())
                : item2.getName().compareToIgnoreCase(item1.getName()));
        return items;
    }

    private static List<MenuItem> searchByName(List<MenuItem> items, String searchName) {
        List<MenuItem> searchResults = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(searchName)) {
                searchResults.add(item);
            }
        }
        return searchResults;
    }

    private static void displayMenuItems(List<MenuItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            for (MenuItem item : items) {
                System.out.println(item);
            }
        }
    }

    static class MainMenu extends JFrame {
        public MainMenu(List<Customer> registeredCustomers, List<MenuItem> menuItems, List<Order> orders) {
            setTitle("Byte Me");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 200);
            setLayout(new GridLayout(4, 1));

            JButton customerLoginButton = new JButton("Login as Customer");
            customerLoginButton.addActionListener(e -> new CustomerLoginPage(Main.registeredCustomers, menuItems));

            JButton adminLoginButton = new JButton("Login as Admin");
            adminLoginButton.addActionListener(e -> new AdminLoginPage(menuItems, orders));

            JButton customerSignupButton = new JButton("Signup as Customer");
            customerSignupButton.addActionListener(e -> SwingUtilities.invokeLater(CustomerSignupPage::new));

            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(e -> System.exit(0));

            add(customerLoginButton);
            add(adminLoginButton);
            add(customerSignupButton);
            add(exitButton);

            setVisible(true);
        }
    }
}
