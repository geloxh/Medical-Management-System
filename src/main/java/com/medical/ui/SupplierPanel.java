package com.medical.ui;

import com.medical.model.Supplier;
import com.medical.util.DatabaseConnection;
import com.medical.util.ReportPrinter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Supplier management panel
 */
public class SupplierPanel extends JPanel {
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, refreshButton, printButton;

    public SupplierPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadSuppliers();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Supplier Management"));

        String[] columns = {"ID", "Name", "Contact", "Email", "Location", "Products Supplied"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        supplierTable = new JTable(tableModel);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierTable.setRowHeight(25);

        addButton = createStyledButton("Add Supplier", new Color(34, 139, 34));
        editButton = createStyledButton("Edit Supplier", new Color(255, 140, 0));
        deleteButton = createStyledButton("Delete Supplier", new Color(220, 20, 60));
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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(refreshButton);
        topPanel.add(printButton);

        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        addButton.addActionListener(e -> showAddSupplierDialog());
        editButton.addActionListener(e -> showEditSupplierDialog());
        deleteButton.addActionListener(e -> deleteSelectedSupplier());
        refreshButton.addActionListener(e -> loadSuppliers());
        printButton.addActionListener(e -> printReport());
    }

    private void showAddSupplierDialog() {
        SupplierDialog dialog = new SupplierDialog(SwingUtilities.getWindowAncestor(this), "Add Supplier", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            addSupplier(dialog.getSupplier());
            loadSuppliers();
        }
    }

    private void showEditSupplierDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to edit.");
            return;
        }

        Supplier supplier = getSupplierFromRow(selectedRow);
        SupplierDialog dialog = new SupplierDialog(SwingUtilities.getWindowAncestor(this), "Edit Supplier", supplier);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            updateSupplier(dialog.getSupplier());
            loadSuppliers();
        }
    }

    private void deleteSelectedSupplier() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this supplier?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int supplierId = (Integer) tableModel.getValueAt(selectedRow, 0);
            deleteSupplier(supplierId);
            loadSuppliers();
        }
    }

    private void loadSuppliers() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers ORDER BY name")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getLong("contact_no"),
                    rs.getString("email_id"),
                    rs.getString("place"),
                    rs.getString("supplied_product")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + e.getMessage());
        }
    }

    private void addSupplier(Supplier supplier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO suppliers (name, contact_no, email_id, place, supplied_product) VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, supplier.getName());
            stmt.setLong(2, supplier.getContactNo());
            stmt.setString(3, supplier.getEmailId());
            stmt.setString(4, supplier.getPlace());
            stmt.setString(5, supplier.getSuppliedProduct());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Supplier added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding supplier: " + e.getMessage());
        }
    }

    private void updateSupplier(Supplier supplier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE suppliers SET name=?, contact_no=?, email_id=?, place=?, supplied_product=? WHERE id=?")) {
            
            stmt.setString(1, supplier.getName());
            stmt.setLong(2, supplier.getContactNo());
            stmt.setString(3, supplier.getEmailId());
            stmt.setString(4, supplier.getPlace());
            stmt.setString(5, supplier.getSuppliedProduct());
            stmt.setInt(6, getSupplierIdFromRow(supplierTable.getSelectedRow()));
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating supplier: " + e.getMessage());
        }
    }

    private void deleteSupplier(int supplierId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM suppliers WHERE id = ?")) {
            
            stmt.setInt(1, supplierId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting supplier: " + e.getMessage());
        }
    }

    private Supplier getSupplierFromRow(int row) {
        Supplier supplier = new Supplier();
        supplier.setName((String) tableModel.getValueAt(row, 1));
        supplier.setContactNo((Long) tableModel.getValueAt(row, 2));
        supplier.setEmailId((String) tableModel.getValueAt(row, 3));
        supplier.setPlace((String) tableModel.getValueAt(row, 4));
        supplier.setSuppliedProduct((String) tableModel.getValueAt(row, 5));
        return supplier;
    }

    private int getSupplierIdFromRow(int row) {
        return (Integer) tableModel.getValueAt(row, 0);
    }

    private void printReport() {
        new ReportPrinter(tableModel, "Suppliers Report").printReport();
    }
}