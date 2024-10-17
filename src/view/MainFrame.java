package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GuestPanel guestPanel;
    private BartenderPanel bartenderPanel;
//    private ManagerPanel managerPanel;

    public MainFrame() {
        setTitle("CaribResort Drink Ordering System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Card layout to switch between views
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        guestPanel = new GuestPanel(null, null);
        bartenderPanel = new BartenderPanel(null);
//        managerPanel = new ManagerPanel(null);

        // Add panels to card layout
        mainPanel.add(guestPanel, "guest");
        mainPanel.add(bartenderPanel, "bartender");
//        mainPanel.add(managerPanel, "manager");

        add(mainPanel);
    }

    // Method to switch between different views
    public void switchPanel(String role) {
        cardLayout.show(mainPanel, role);
    }
}
