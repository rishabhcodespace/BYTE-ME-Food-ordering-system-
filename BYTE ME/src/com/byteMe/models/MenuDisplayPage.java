package com.byteMe.models;
import com.byteMe.models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuDisplayPage extends JFrame {
    private List<MenuItem> menuItems;

    public MenuDisplayPage(List<MenuItem> items) {
        this.menuItems = items;

        setTitle("Canteen Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Table for displaying menu items
        String[] columnNames = {"Item Name", "Price", "Available"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (MenuItem item : menuItems) {
            tableModel.addRow(new Object[]{item.getName(), item.getPrice(), item.isAvailable() ? "Yes" : "No"});
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
