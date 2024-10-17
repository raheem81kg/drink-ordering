package view;

import javax.swing.*;

import controller.AuthenticationController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -1797816622096216194L;
	private AuthenticationController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;

    public LoginFrame(MainFrame mainFrame, AuthenticationController authController) {
        this.authController = authController;
        this.mainFrame = mainFrame;

        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty space
        panel.add(loginButton);

        add(panel);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authController.login(username, password)) {
                    String role = authController.getUserRole(username);

                    // Switch panel based on user role
                    if ("bartender".equalsIgnoreCase(role)) {
                        mainFrame.switchPanel("bartender");
                    } else if ("manager".equalsIgnoreCase(role)) {
                        mainFrame.switchPanel("manager");
                    }
                    dispose();  // Close login window
                    mainFrame.setVisible(true);  // Show main window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials");
                }
            }
        });
    }
}
