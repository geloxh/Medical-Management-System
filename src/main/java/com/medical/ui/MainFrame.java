package com.medical.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application frame with modern tabbed interface
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private ProductPanel productPanel;
    private SupplierPanel supplierPanel;
    private SalesPanel salesPanel;
    private PurchasePanel purchasePanel;

    public MainFrame() {
        initializeComponents();
        setupLayout();
        setupMenuBar();
    }

    private void initializeComponents() {
        setTitle("Medical Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 700));

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Initialize panels
        productPanel = new ProductPanel();
        supplierPanel = new SupplierPanel();
        salesPanel = new SalesPanel();
        purchasePanel = new PurchasePanel();
    }

    private void setupLayout() {
        // Add tabs with icons
        tabbedPane.addTab("Products", createIcon("📦"), productPanel, "Manage Products");
        tabbedPane.addTab("Suppliers", createIcon("🏢"), supplierPanel, "Manage Suppliers");
        tabbedPane.addTab("Purchase", createIcon("🛒"), purchasePanel, "Purchase Management");
        tabbedPane.addTab("Sales Record", createIcon("📊"), salesPanel, "View Sales Records");

        add(tabbedPane, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.add(new JLabel("Ready"));
        add(statusBar, BorderLayout.SOUTH);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private Icon createIcon(String emoji) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                g.drawString(emoji, x, y + 12);
            }
            @Override
            public int getIconWidth() { return 20; }
            @Override
            public int getIconHeight() { return 16; }
        };
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Medical Management System v2.0\n" +
            "Professional pharmacy inventory management\n" +
            "Built with Java Swing",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }
}