package view;

import javax.swing.*;

import controller.DrinkController;
import controller.OrderController;
import model.Drink;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuestPanel extends JPanel {
    private DrinkController drinkController;
    private OrderController orderController;
    private JComboBox<String> drinkComboBox;
    private JTextField quantityField;
    private JTextField guestNameField;
    private JTextField tableNumberField;

    public GuestPanel(DrinkController drinkController, OrderController orderController) {
        this.drinkController = drinkController;
        this.orderController = orderController;

        setLayout(new GridLayout(6, 2));

        JLabel guestNameLabel = new JLabel("Guest Name:");
        guestNameField = new JTextField();

        JLabel tableNumberLabel = new JLabel("Table Number:");
        tableNumberField = new JTextField();

        JLabel drinkLabel = new JLabel("Select Drink:");
        drinkComboBox = new JComboBox<>();

        // Fetch available drinks from DrinkController
        List<Drink> drinks = drinkController.getAllDrinks();
        for (Drink drink : drinks) {
            drinkComboBox.addItem(drink.getDrinkName());
        }

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();

        JButton submitOrderButton = new JButton("Submit Order");

        // Add components to panel
        add(guestNameLabel);
        add(guestNameField);
        add(tableNumberLabel);
        add(tableNumberField);
        add(drinkLabel);
        add(drinkComboBox);
        add(quantityLabel);
        add(quantityField);
        add(new JLabel());  // Empty space
        add(submitOrderButton);

        // Submit order action
        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guestName = guestNameField.getText();
                int tableNumber = Integer.parseInt(tableNumberField.getText());
                String selectedDrink = (String) drinkComboBox.getSelectedItem();
                int quantity = Integer.parseInt(quantityField.getText());

                // Submit the order using the OrderController
                int drinkId = drinkController.getDrinkIdByName(selectedDrink);
                orderController.submitOrder(guestName, tableNumber, 1, List.of(drinkId), List.of(quantity));

                JOptionPane.showMessageDialog(null, "Order Submitted Successfully");
            }
        });
    }
}
