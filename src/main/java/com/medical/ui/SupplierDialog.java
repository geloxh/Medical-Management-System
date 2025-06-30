package com.medical.ui;

import com.medical.model.Supplier;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding/editing suppliers
 */
public class SupplierDialog extends JDialog {
    private JTextField nameField, contactField, emailField, placeField, productField;
    private JButton saveButton, cancelButton;
    private boolean confirmed = false;
    private Supplier supplier;

    public SupplierDialog(Window parent, String title, Supplier supplier) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.supplier = supplier;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        if (supplier != null) {
            populateFields();
        }
    }

    private void initializeComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        nameField = new JTextField(20);
        contactField = new JTextField(20);
        emailField = new JTextField(20);
        placeField = new JTextField(20);
        productField = new JTextField(20);

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
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Location:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(placeField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Products Supplied:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(productField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
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
        nameField.setText(supplier.getName());
        contactField.setText(String.valueOf(supplier.getContactNo()));
        emailField.setText(supplier.getEmailId());
        placeField.setText(supplier.getPlace());
        productField.setText(supplier.getSuppliedProduct());
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }

        try {
            Long.parseLong(contactField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid contact number.");
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Supplier getSupplier() {
        if (supplier == null) {
            supplier = new Supplier();
        }
        
        supplier.setName(nameField.getText().trim());
        supplier.setContactNo(Long.parseLong(contactField.getText().trim()));
        supplier.setEmailId(emailField.getText().trim());
        supplier.setPlace(placeField.getText().trim());
        supplier.setSuppliedProduct(productField.getText().trim());
        
        return supplier;
    }
}