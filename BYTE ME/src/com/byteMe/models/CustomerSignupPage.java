package com.byteMe.models;

import com.byteMe.customer.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSignupPage extends JFrame {
    private static List<Object> registeredCustomers = new ArrayList<>().reversed();

    public CustomerSignupPage() {
        setTitle("Customer Signup");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(nameField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(phoneField, gbc);

        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addressLabel, gbc);

        JTextField addressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(addressField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(genderLabel, gbc);

        JTextField genderField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(genderField, gbc);

        JLabel vipLabel = new JLabel("VIP Customer (yes/no):");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(vipLabel, gbc);

        JTextField vipField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(vipField, gbc);

        JButton signupButton = new JButton("Sign Up");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(signupButton, gbc);

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        signupButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String gender = genderField.getText();
            boolean isVip = vipField.getText().equalsIgnoreCase("yes");

            if (!isValidEmail(email)) {
                statusLabel.setText("Invalid email format. Please try again.");
                return;
            }

            try {
                // Creating new customer and then retrieving
                Customer customer = new Customer(email, password, name, phone, address, gender, isVip);
                registeredCustomers.add(customer);
                JOptionPane.showMessageDialog(this, "Customer signup successful!");
                dispose();
            } catch (IllegalArgumentException ex) {
                statusLabel.setText("Error during signup: " + ex.getMessage());
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerSignupPage::new);
    }
}
