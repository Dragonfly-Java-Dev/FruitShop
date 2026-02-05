package com.isuru.gui;

import com.isuru.util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnClear;

    public InventoryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel lblTitle = new JLabel("Available Fruits Details", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 51, 153)); // Dark blue
        add(lblTitle, BorderLayout.NORTH);

        // Table with DefaultTableModel
        String[] columnNames = { "Name", "Price (Rs.)", "Amount (kg)" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(33, 150, 243)); // Blue header
        table.getTableHeader().setForeground(Color.WHITE);

        // Scroll Pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Clear Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        btnClear = new JButton("Clear Fruits");
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.setBackground(new Color(76, 175, 80)); // Green
        btnClear.setForeground(Color.WHITE);
        btnClear.setPreferredSize(new Dimension(120, 40));
        btnClear.setFocusPainted(false);
        btnClear.setBorderPainted(false);
        btnClear.addActionListener(e -> clearTable());

        buttonPanel.add(btnClear);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data initially
        loadInventoryData();
    }

    /**
     * Load inventory data from database
     */
    public void loadInventoryData() {
        tableModel.setRowCount(0); // Clear existing rows

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            String query = "SELECT name, price, amount FROM fruits ORDER BY name";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double amount = rs.getDouble("amount");

                tableModel.addRow(new Object[] { name, String.format("%.2f", price), String.format("%.2f", amount) });
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Clear all fruits from the database
     */
    private void clearTable() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete ALL fruits from the database?\nThis action cannot be undone.",
                "Confirm Delete All",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM fruits");
                stmt.close();

                JOptionPane.showMessageDialog(this,
                        "All fruits have been deleted.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                loadInventoryData();
                refreshOtherPanels();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    /**
     * Refresh other panels (billing and update)
     */
    private void refreshOtherPanels() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }

        if (parent instanceof MainFrame) {
            ((MainFrame) parent).refreshPanels();
        }
    }
}
