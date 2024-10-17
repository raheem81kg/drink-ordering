package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.OrderController;
import model.Order;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BartenderPanel extends JPanel {
    private static final long serialVersionUID = 6540247391562820151L;
	private OrderController orderController;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public BartenderPanel(OrderController orderController) {
        this.orderController = orderController;

        setLayout(new BorderLayout());

        // Table to display orders
        String[] columnNames = {"Order ID", "Guest Name", "Table", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);

        // Refresh orders button
        JButton refreshButton = new JButton("Refresh Orders");

        // Add table and button to panel
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        // Refresh button action
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshOrders();
            }
        });

        // Load initial orders
        refreshOrders();
    }

    // Method to refresh the order list
    private void refreshOrders() {
        tableModel.setRowCount(0);  // Clear existing rows

        // Get open orders from the OrderController
        List<Order> orders = orderController.getOpenOrders();
        for (Order order : orders) {
            tableModel.addRow(new Object[]{order.getOrderID(), order.getGuestName(), order.getTableNumber(), order.getOrderStatus()});


 }
    }
}
