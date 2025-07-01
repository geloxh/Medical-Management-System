package com.medical.ui;

import com.medical.model.User;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding/editing users
 */
public class UserDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton, cancelButton;
    private boolean confirmed = false;
    private User user;
    private boolean isEdit;

    public UserDialog(Window parent, String title, User user) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.user = user;
        this.isEdit = (user != null);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        if (user != null) {
            populateFields();
        }
    }

    private void initializeComponents() {
        setSize(350, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(new String[]{"User", "Admin", "Manager"});

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        if (!isEdit) {
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
            add(new JLabel("Password:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            add(passwordField, gbc);
        }

        gbc.gridx = 0; gbc.gridy = isEdit ? 1 : 2; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(roleComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = isEdit ? 2 : 3; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void setupEventHandlers() {
        saveButton.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        usernameField.setText(user.getUsername());
        roleComboBox.setSelectedItem(user.getRole());
    }

    private boolean validateFields() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is required.");
            return false;
        }

        if (!isEdit && passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Password is required.");
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        
        user.setUsername(usernameField.getText().trim());
        if (!isEdit) {
            user.setPassword(new String(passwordField.getPassword()));
        }
        user.setRole((String) roleComboBox.getSelectedItem());
        
        return user;
    }
}