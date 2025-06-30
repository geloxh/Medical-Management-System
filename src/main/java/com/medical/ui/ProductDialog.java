package com.medical.ui;

import com.medical.model.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Dialog for adding/editing products
 */
public class ProductDialog extends JDialog {
    private JTextField nameField, typeField, companyField, locationField, quantityField, priceField;
    private JTextField expiryField;
    private JButton saveButton, cancelButton;
    private boolean confirmed = false;
    private Product product;

    public ProductDialog(Window parent, String title, Product product) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.product = product;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        if (product != null) {
            populateFields();
        }
    }

    private void initializeComponents() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);

        nameField = new JTextField(20);
        typeField = new JTextField(20);
        companyField = new JTextField(20);
        locationField = new JTextField(20);
        quantityField = new JTextField(20);
        priceField = new JTextField(20);
        expiryField = new JTextField(20);

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

        // Product Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);

        // Product Type
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Product Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typeField, gbc);

        // Company
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Company:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(companyField, gbc);

        // Location
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Storage Location:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(locationField, gbc);

        // Expiry Date
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Expiry Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(expiryField, gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(quantityField, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(priceField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void setupEventHandlers() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    confirmed = true;
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        nameField.setText(product.getProductName());
        typeField.setText(product.getProductType());
        companyField.setText(product.getCompany());
        locationField.setText(product.getStoredAt());
        expiryField.setText(product.getExpiryDate().toString());
        quantityField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name is required.");
            return false;
        }

        try {
            Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            return false;
        }

        try {
            Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            return false;
        }

        try {
            LocalDate.parse(expiryField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date (YYYY-MM-DD).");
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Product getProduct() {
        if (product == null) {
            product = new Product();
        }
        
        product.setProductName(nameField.getText().trim());
        product.setProductType(typeField.getText().trim());
        product.setCompany(companyField.getText().trim());
        product.setStoredAt(locationField.getText().trim());
        product.setExpiryDate(LocalDate.parse(expiryField.getText().trim()));
        product.setQuantity(Integer.parseInt(quantityField.getText().trim()));
        product.setPrice(Double.parseDouble(priceField.getText().trim()));
        
        return product;
    }
}