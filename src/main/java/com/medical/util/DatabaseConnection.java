package com.medical.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USERNAME, PASSWORD)) {
            conn.createStatement().execute("CREATE DATABASE IF NOT EXISTS medical_system");
            
            try (Connection dbConn = getConnection()) {
                // Create products table
                dbConn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS products(" +
                    "product_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "product_name VARCHAR(100) NOT NULL," +
                    "product_type VARCHAR(50)," +
                    "expiry_date DATE," +
                    "company VARCHAR(100)," +
                    "stored_at VARCHAR(50)," +
                    "quantity INT DEFAULT 0," +
                    "price DECIMAL(10,2)" +
                    ")"
                );
                
                // Create suppliers table
                dbConn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS suppliers(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "contact_no BIGINT," +
                    "email_id VARCHAR(100)," +
                    "place VARCHAR(100)," +
                    "supplied_product VARCHAR(100)" +
                    ")"
                );
                
                // Create sales table
                dbConn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS sales(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "product_name VARCHAR(100)," +
                    "expiry_date DATE," +
                    "quantity INT," +
                    "price DECIMAL(10,2)," +
                    "purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}