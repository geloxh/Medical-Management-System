package com.medical.ui;

import com.medical.util.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Purchase management panel
 */
public class PurchasePanel extends JPanel {
    private JTextField searchField, nameField, quantityField, priceField;
    private JButton searchButton, addButton, clearButton;
    private JTable purchaseTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private double totalAmount = 0.0;

    public PurchasePanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Purchase Management"));

        // Search components
        searchField = new JTextField(30);
        searchButton = new JButton("Search Product");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        // Input fields
        nameField = new JTextField(15);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        // Buttons
        addButton = new JButton("Add to Cart");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        clearButton = new JButton("Clear Cart");
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);

        // Table
        String[] columns = {"Product Name", "Quantity", "Unit Price", "Total Price", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        purchaseTable = new JTable(tableModel);
        purchaseTable.setRowHeight(25);

        // Total label
        totalLabel = new JLabel("Total Amount: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(0, 100, 0));
    }

    private void setupLayout() {
        // Top panel - Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search Product:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Product to Cart"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Unit Price:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(priceField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 3;
        inputPanel.add(clearButton, gbc);

        // Main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        // Table panel
        JScrollPane scrollPane = new JScrollPane(purchaseTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Purchase Cart"));

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(totalLabel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        searchButton.addActionListener(e -> searchProduct());
        addButton.addActionListener(e -> addToCart());
        clearButton.addActionListener(e -> clearCart());
    }

    private void searchProduct() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name to search.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT product_name, price FROM products WHERE product_name LIKE ? LIMIT 1")) {
            
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                nameField.setText(rs.getString("product_name"));
                priceField.setText(String.valueOf(rs.getDouble("price")));
                quantityField.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching product: " + e.getMessage());
        }
    }

    private void addToCart() {
        if (!validateInput()) return;

        String productName = nameField.getText().trim();
        int quantity = Integer.parseInt(quantityField.getText().trim());
        double unitPrice = Double.parseDouble(priceField.getText().trim());
        double totalPrice = quantity * unitPrice;
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Add to table
        Object[] row = {productName, quantity, String.format("$%.2f", unitPrice), 
                       String.format("$%.2f", totalPrice), currentTime};
        tableModel.addRow(row);

        // Update total
        totalAmount += totalPrice;
        totalLabel.setText(String.format("Total Amount: $%.2f", totalAmount));

        // Save to database
        savePurchase(productName, quantity, unitPrice, totalPrice);

        // Clear input fields
        clearInputFields();
    }

    private void savePurchase(String productName, int quantity, double unitPrice, double totalPrice) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO sales (product_name, quantity, price, purchase_date) VALUES (?, ?, ?, NOW())")) {
            
            stmt.setString(1, productName);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, totalPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving purchase: " + e.getMessage());
        }
    }

    private void clearCart() {
        tableModel.setRowCount(0);
        totalAmount = 0.0;
        totalLabel.setText("Total Amount: $0.00");
        clearInputFields();
    }

    private void clearInputFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        searchField.setText("");
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name is required.");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            return false;
        }

        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            return false;
        }

        return true;
    }
}