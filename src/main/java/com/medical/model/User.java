package com.medical.model;

import java.time.LocalDateTime;

/**
 * User model class
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createdDate;

    public User() {}

    public User(int id, String username, String password, String role, LocalDateTime createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}