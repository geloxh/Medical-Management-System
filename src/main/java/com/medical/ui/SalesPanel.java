package com.medical.ui;

import com.medical.util.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Sales record viewing panel
 */
public class SalesPanel extends JPanel {
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, exportButton;
    private JLabel totalSalesLabel;

    public SalesPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadSalesData();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Sales Records"));

        // Table
        String[] columns = {"ID", "Product Name", "Quantity", "Price", "Purchase Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(tableModel);
        salesTable.setRowHeight(25);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Buttons
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);

        exportButton = new JButton("Export to CSV");
        exportButton.setBackground(new Color(34, 139, 34));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);

        // Total sales label
        totalSalesLabel = new JLabel("Total Sales: $0.00");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalSalesLabel.setForeground(new Color(0, 100, 0));
    }

    private void setupLayout() {
        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        topPanel.add(exportButton);

        // Table
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(totalSalesLabel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        refreshButton.addActionListener(e -> loadSalesData());
        exportButton.addActionListener(e -> exportToCSV());
    }

    private void loadSalesData() {
        tableModel.setRowCount(0);
        double totalSales = 0.0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sales ORDER BY purchase_date DESC")) {
            
            while (rs.next()) {
                double price = rs.getDouble("price");
                totalSales += price;
                
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    String.format("$%.2f", price),
                    rs.getTimestamp("purchase_date")
                };
                tableModel.addRow(row);
            }
            
            totalSalesLabel.setText(String.format("Total Sales: $%.2f", totalSales));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading sales data: " + e.getMessage());
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Sales Report");
        fileChooser.setSelectedFile(new java.io.File("sales_report.csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(fileChooser.getSelectedFile())) {
                // Write header
                writer.println("ID,Product Name,Quantity,Price,Purchase Date");
                
                // Write data
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        writer.print(tableModel.getValueAt(i, j));
                        if (j < tableModel.getColumnCount() - 1) {
                            writer.print(",");
                        }
                    }
                    writer.println();
                }
                
                JOptionPane.showMessageDialog(this, "Sales report exported successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage());
            }
        }
    }
}