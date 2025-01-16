package com.byteMe.models;
import com.byteMe.models.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PendingOrdersPage extends JFrame {
    private Queue<Order> orderQueue = new PriorityQueue<>((o1, o2) -> Boolean.compare(o2.isVip(), o1.isVip()));

    public PendingOrdersPage(List<Order> orders) {
//        this.pendingOrders = orders;
        setTitle("Pending Orders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Order ID ", "Items Ordered", "vip", " order status"};

        // Prepared the data for the table
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Order order :Order.getOrderQueue()) {
            tableModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getItems().toString(), // You can customize this to show a list of items
                    order.isVip() ? "Yes" : "No",
                    order.getStatus()
            });
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        //just adding this if i need it later
    }
}