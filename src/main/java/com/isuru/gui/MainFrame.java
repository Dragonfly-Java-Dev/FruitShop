package com.isuru.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private InventoryPanel inventoryPanel;
    private BillingPanel billingPanel;
    private UpdatePanel updatePanel;

    public MainFrame() {
        // Frame configuration
        setTitle("Fruit Shop Management System");

        // Set window icon
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
        }

        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Set global properties before component creation
        UIManager.put("TabbedPane.selected", new Color(255, 87, 34)); // Orange-red
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setBackground(new Color(255, 87, 34));

        // Create panels
        inventoryPanel = new InventoryPanel();
        billingPanel = new BillingPanel();
        updatePanel = new UpdatePanel();

        // Add tabs
        tabbedPane.addTab("Inventory", inventoryPanel);
        tabbedPane.addTab("Billing", billingPanel);
        tabbedPane.addTab("Update", updatePanel);

        // Explicitly set background for all tabs
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setBackgroundAt(i, new Color(255, 87, 34));
            tabbedPane.setForegroundAt(i, Color.WHITE);
        }

        // Add tabbed pane to frame
        add(tabbedPane);

        // Make visible
        setVisible(true);
    }

    /**
     * Refresh all panels (called after CRUD operations)
     */
    public void refreshPanels() {
        // Refresh inventory table
        inventoryPanel.loadInventoryData();

        // Refresh billing combo box
        billingPanel.loadFruitComboBox();

        // Refresh update combo boxes
        updatePanel.loadFruitComboBoxes();
    }
}
