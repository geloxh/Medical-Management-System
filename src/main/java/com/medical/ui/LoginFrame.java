package com.medical.ui;

import com.medical.util.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Professional login frame for the medical management system
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel mainPanel;

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        DatabaseConnection.initializeDatabase();
    }

    private void initializeComponents() {
        setTitle("Medical Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        
        // Style components
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
    }

    private void setupLayout() {
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Medical Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
    }

    private void setupEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Simple authentication (in real app, use proper authentication)
                if ("admin".equals(username) && "admin".equals(password)) {
                    dispose();
                    new MainFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Invalid credentials. Use admin/admin", 
                        "Login Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Enter key support
        getRootPane().setDefaultButton(loginButton);
    }
}