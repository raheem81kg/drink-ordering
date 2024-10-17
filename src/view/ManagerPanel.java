package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.DrinkController;
import model.Drink;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerPanel extends JPanel {
    private DrinkController drinkController;
    private JTable drinksTable;
    private DefaultTableModel tableModel;
    private ManagerReportView managerReportView;

    public ManagerPanel(DrinkController drinkController) {
        this.drinkController = drinkController;
        setLayout(new BorderLayout());

        // Initialize ManagerReportView and set it invisible by default
        managerReportView = new ManagerReportView(null);
        managerReportView.setVisible(false);

        // Table to display drinks
        String[] columnNames = {"Drink ID", "Drink Name", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        drinksTable = new JTable(tableModel);

        // Add, Edit, Delete, and View Report buttons
        JButton addDrinkButton = new JButton("Add Drink");
        JButton editDrinkButton = new JButton("Edit Drink");
        JButton deleteDrinkButton = new JButton("Delete Drink");
        JButton viewReportButton = new JButton("View Report");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addDrinkButton);
        buttonPanel.add(editDrinkButton);
        buttonPanel.add(deleteDrinkButton);
        buttonPanel.add(viewReportButton);

        // Add components to panel
        add(new JScrollPane(drinksTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(managerReportView, BorderLayout.EAST);  // Add report view to the right side (initially hidden)

        // Load initial drinks
        refreshDrinks();

        // Add drink action
        addDrinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String drinkName = JOptionPane.showInputDialog("Enter Drink Name:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Enter Drink Price:"));

                // Add the drink via DrinkController
                drinkController.addDrink(drinkName, price);
                refreshDrinks();
            }
        });

        // Edit drink action
        editDrinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = drinksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String drinkName = (String) tableModel.getValueAt(selectedRow, 1);
                    double price = (double) tableModel.getValueAt(selectedRow, 2);

                    String newDrinkName = JOptionPane.showInputDialog("Edit Drink Name:", drinkName);
                    double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Edit Drink Price:", price));

                    int drinkId = (int) tableModel.getValueAt(selectedRow, 0);
                    drinkController.updateDrink(drinkId, newDrinkName, newPrice);
                    refreshDrinks();
                }
            }
        });

        // Delete drink action
        deleteDrinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = drinksTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int drinkId = (int) tableModel.getValueAt(selectedRow, 0);
                    drinkController.deleteDrink(drinkId);
                    refreshDrinks();
                }
            }
        });

        // View Report action
        viewReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle visibility of ManagerReportView
                managerReportView.setVisible(!managerReportView.isVisible());
                revalidate();  // Repaint the panel to reflect the visibility change
            }
        });
    }

    // Method to refresh the drinks list
    private void refreshDrinks() {
        tableModel.setRowCount(0);  // Clear existing rows

        // Get all drinks from DrinkController
        List<Drink> drinks = drinkController.getAllDrinks();
        for (Drink drink : drinks) {
            tableModel.addRow(new Object[]{drink.getDrinkId(), drink.getDrinkName(), drink.getPrice()});
        }
    }
}
