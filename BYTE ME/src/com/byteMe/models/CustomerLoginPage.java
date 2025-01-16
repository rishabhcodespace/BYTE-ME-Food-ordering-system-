package com.byteMe.models;

import com.byteMe.customer.Customer;
import com.byteMe.models.MenuDisplayPage;
import com.byteMe.models.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerLoginPage extends JFrame {
    private List<Customer> registeredCustomers;
    private List<MenuItem> menuItems;

    public CustomerLoginPage(List<Customer> customers, List<MenuItem> items) {
        this.registeredCustomers = customers;
        this.menuItems = items;

        setTitle("Customer Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin(emailField.getText(), new String(passwordField.getPassword())));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(cancelButton);

        setVisible(true);
    }

    private void handleLogin(String email, String password) {
        try {
            Customer customer = authenticateCustomer(email, password);
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MenuDisplayPage(menuItems); // Display menu items
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Customer authenticateCustomer(String email, String password) {
        for (Customer customer : registeredCustomers) {
            if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        throw new IllegalArgumentException("Invalid email or password.");
    }
}
