package com.byteMe.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminLoginPage extends JFrame {
    private List<MenuItem> menuItems;
    private List<Order> orders;

    public AdminLoginPage(List<MenuItem> menuItems, List<Order> orders) {
        this.menuItems = menuItems;
        this.orders = orders;

        setTitle("Admin Login");
        setSize(400, 300);
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

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Validate the  admin credentials
                if (email.equals("admin@byteme") && password.equals("admin1234")) {
                    JOptionPane.showMessageDialog(AdminLoginPage.this, "Admin login successful!");
                    dispose(); // Close the login page

                    // this will Redirect to pages like MenuDisplayPage and PendingOrdersPage with arguments
                    SwingUtilities.invokeLater(() -> new MenuDisplayPage(menuItems));
                    SwingUtilities.invokeLater(() -> new PendingOrdersPage(orders));
                } else {
                    statusLabel.setText("Invalid email or password. Please try again.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // for now,  I'm not adding anything will see if required
    }
}
