package com.medical;

import com.medical.ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point for the Medical Management System
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}