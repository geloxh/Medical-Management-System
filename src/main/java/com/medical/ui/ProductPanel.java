package com.medical.ui;

import com.medical.model.Product;
import com.medical.util.DatabaseConnection;
import com.medical.util.ReportPrinter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

/**
 * Professional product management panel
 */
public class ProductPanel extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, refreshButton, printButton;

    public ProductPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadProducts();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Product Management"));

        // Table setup
        String[] columns = {"ID", "Name", "Type", "Expiry Date", "Company", "Location", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(25);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Search field
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Buttons
        addButton = createStyledButton("Add Product", new Color(34, 139, 34));
        editButton = createStyledButton("Edit Product", new Color(255, 140, 0));
        deleteButton = createStyledButton("Delete Product", new Color(220, 20, 60));
        refreshButton = createStyledButton("Refresh", new Color(70, 130, 180));
        printButton = createStyledButton("Print Report", new Color(128, 0, 128));
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private void setupLayout() {
        // Top panel with search and buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(refreshButton);
        topPanel.add(printButton);

        // Table with scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        addButton.addActionListener(e -> showAddProductDialog());
        editButton.addActionListener(e -> showEditProductDialog());
        deleteButton.addActionListener(e -> deleteSelectedProduct());
        refreshButton.addActionListener(e -> loadProducts());
        
        searchField.addActionListener(e -> searchProducts());
        printButton.addActionListener(e -> printReport());
    }

    private void showAddProductDialog() {
        ProductDialog dialog = new ProductDialog(SwingUtilities.getWindowAncestor(this), "Add Product", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Product product = dialog.getProduct();
            addProduct(product);
            loadProducts();
        }
    }

    private void showEditProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            return;
        }

        Product product = getProductFromRow(selectedRow);
        ProductDialog dialog = new ProductDialog(SwingUtilities.getWindowAncestor(this), "Edit Product", product);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            updateProduct(dialog.getProduct());
            loadProducts();
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this product?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
            deleteProduct(productId);
            loadProducts();
        }
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products ORDER BY product_name")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_type"),
                    rs.getDate("expiry_date"),
                    rs.getString("company"),
                    rs.getString("stored_at"),
                    rs.getInt("quantity"),
                    String.format("$%.2f", rs.getDouble("price"))
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void addProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO products (product_name, product_type, expiry_date, company, stored_at, quantity, price) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductType());
            stmt.setDate(3, Date.valueOf(product.getExpiryDate()));
            stmt.setString(4, product.getCompany());
            stmt.setString(5, product.getStoredAt());
            stmt.setInt(6, product.getQuantity());
            stmt.setDouble(7, product.getPrice());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage());
        }
    }

    private void updateProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE products SET product_name=?, product_type=?, expiry_date=?, company=?, stored_at=?, quantity=?, price=? WHERE product_id=?")) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductType());
            stmt.setDate(3, Date.valueOf(product.getExpiryDate()));
            stmt.setString(4, product.getCompany());
            stmt.setString(5, product.getStoredAt());
            stmt.setInt(6, product.getQuantity());
            stmt.setDouble(7, product.getPrice());
            stmt.setInt(8, product.getProductId());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating product: " + e.getMessage());
        }
    }

    private void deleteProduct(int productId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")) {
            
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage());
        }
    }

    private Product getProductFromRow(int row) {
        Product product = new Product();
        product.setProductId((Integer) tableModel.getValueAt(row, 0));
        product.setProductName((String) tableModel.getValueAt(row, 1));
        product.setProductType((String) tableModel.getValueAt(row, 2));
        product.setExpiryDate(((Date) tableModel.getValueAt(row, 3)).toLocalDate());
        product.setCompany((String) tableModel.getValueAt(row, 4));
        product.setStoredAt((String) tableModel.getValueAt(row, 5));
        product.setQuantity((Integer) tableModel.getValueAt(row, 6));
        String priceStr = (String) tableModel.getValueAt(row, 7);
        product.setPrice(Double.parseDouble(priceStr.replace("$", "")));
        return product;
    }

    private void searchProducts() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadProducts();
            return;
        }

        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM products WHERE product_name LIKE ? OR company LIKE ? ORDER BY product_name")) {
            
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_type"),
                    rs.getDate("expiry_date"),
                    rs.getString("company"),
                    rs.getString("stored_at"),
                    rs.getInt("quantity"),
                    String.format("$%.2f", rs.getDouble("price"))
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching products: " + e.getMessage());
        }
    }

    private void printReport() {
        new ReportPrinter(tableModel, "Products Report").printReport();
    }
}