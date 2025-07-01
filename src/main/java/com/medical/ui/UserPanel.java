package com.medical.ui;

import com.medical.model.User;
import com.medical.util.DatabaseConnection;
import com.medical.util.ReportPrinter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * User management panel
 */
public class UserPanel extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, refreshButton, printButton;

    public UserPanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadUsers();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("User Management"));

        String[] columns = {"ID", "Username", "Role", "Created Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(25);

        addButton = createStyledButton("Add User", new Color(34, 139, 34));
        editButton = createStyledButton("Edit User", new Color(255, 140, 0));
        deleteButton = createStyledButton("Delete User", new Color(220, 20, 60));
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

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        addButton.addActionListener(e -> showAddUserDialog());
        editButton.addActionListener(e -> showEditUserDialog());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        refreshButton.addActionListener(e -> loadUsers());
        printButton.addActionListener(e -> printReport());
    }

    private void showAddUserDialog() {
        UserDialog dialog = new UserDialog(SwingUtilities.getWindowAncestor(this), "Add User", null);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            addUser(dialog.getUser());
            loadUsers();
        }
    }

    private void showEditUserDialog() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        User user = getUserFromRow(selectedRow);
        UserDialog dialog = new UserDialog(SwingUtilities.getWindowAncestor(this), "Edit User", user);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            updateUser(dialog.getUser());
            loadUsers();
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRow, 1);
        if ("admin".equals(username)) {
            JOptionPane.showMessageDialog(this, "Cannot delete admin user.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this user?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int userId = (Integer) tableModel.getValueAt(selectedRow, 0);
            deleteUser(userId);
            loadUsers();
        }
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY username")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getTimestamp("created_date")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage());
        }
    }

    private void addUser(User user) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "User added successfully!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
            }
        }
    }

    private void updateUser(User user) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE users SET username=?, role=? WHERE id=?")) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getRole());
            stmt.setInt(3, user.getId());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "User updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser(int userId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
        }
    }

    private User getUserFromRow(int row) {
        User user = new User();
        user.setId((Integer) tableModel.getValueAt(row, 0));
        user.setUsername((String) tableModel.getValueAt(row, 1));
        user.setRole((String) tableModel.getValueAt(row, 2));
        return user;
    }

    private void printReport() {
        new ReportPrinter(tableModel, "Users Report").printReport();
    }
}