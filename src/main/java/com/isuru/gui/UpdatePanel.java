package com.isuru.gui;

import com.isuru.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePanel extends JPanel {
    // Add New Fruit components
    private JTextField txtAddName;
    private JTextField txtAddPrice;
    private JTextField txtAddAmount;
    private JButton btnSave;

    // Update Existing Fruit components
    private JComboBox<String> cmbUpdateName;
    private JTextField txtUpdatePrice;
    private JTextField txtUpdateAmount;
    private JButton btnUpdate;

    // Delete Fruit components
    private JComboBox<String> cmbDeleteName;
    private JButton btnDelete;

    public UpdatePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== ADD NEW FRUIT SECTION =====
        JPanel addSection = createSection("Add New Fruit");
        JPanel addInputPanel = new JPanel(new GridBagLayout());
        addInputPanel.setBackground(new Color(220, 220, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fruit Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        addInputPanel.add(new JLabel("Fruit Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtAddName = new JTextField(20);
        txtAddName.setFont(new Font("Arial", Font.PLAIN, 14));
        addInputPanel.add(txtAddName, gbc);

        // Price
        gbc.gridx = 2;
        gbc.weightx = 0;
        addInputPanel.add(new JLabel("Price Of One:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        txtAddPrice = new JTextField(15);
        txtAddPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        addInputPanel.add(txtAddPrice, gbc);

        // Amount
        gbc.gridx = 4;
        gbc.weightx = 0;
        addInputPanel.add(new JLabel("Amount (kg):"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 1.0;
        txtAddAmount = new JTextField(15);
        txtAddAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        addInputPanel.add(txtAddAmount, gbc);

        // Save Button
        gbc.gridx = 6;
        gbc.weightx = 0;
        btnSave = createButton("Save", new Color(46, 125, 50));
        btnSave.addActionListener(e -> addFruit());
        addInputPanel.add(btnSave, gbc);

        addSection.add(addInputPanel, BorderLayout.CENTER);
        add(addSection);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== UPDATE EXISTING FRUIT SECTION =====
        JPanel updateSection = createSection("Update Existing Fruit");
        JPanel updateInputPanel = new JPanel(new GridBagLayout());
        updateInputPanel.setBackground(new Color(220, 220, 220));

        // Select Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        updateInputPanel.add(new JLabel("Select Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbUpdateName = new JComboBox<>();
        cmbUpdateName.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbUpdateName.addActionListener(e -> loadFruitDetails());
        updateInputPanel.add(cmbUpdateName, gbc);

        // Price
        gbc.gridx = 2;
        gbc.weightx = 0;
        updateInputPanel.add(new JLabel("Price Of One:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        txtUpdatePrice = new JTextField(15);
        txtUpdatePrice.setFont(new Font("Arial", Font.PLAIN, 14));
        updateInputPanel.add(txtUpdatePrice, gbc);

        // Amount
        gbc.gridx = 4;
        gbc.weightx = 0;
        updateInputPanel.add(new JLabel("Amount (kg):"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 1.0;
        txtUpdateAmount = new JTextField(15);
        txtUpdateAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        updateInputPanel.add(txtUpdateAmount, gbc);

        // Update Button
        gbc.gridx = 6;
        gbc.weightx = 0;
        btnUpdate = createButton("Update", new Color(46, 125, 50));
        btnUpdate.addActionListener(e -> updateFruit());
        updateInputPanel.add(btnUpdate, gbc);

        updateSection.add(updateInputPanel, BorderLayout.CENTER);
        add(updateSection);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== DELETE FRUIT SECTION =====
        JPanel deleteSection = createSection("Delete Fruit From Inventory");
        JPanel deleteInputPanel = new JPanel(new GridBagLayout());
        deleteInputPanel.setBackground(new Color(220, 220, 220));

        // Select Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        deleteInputPanel.add(new JLabel("Select Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbDeleteName = new JComboBox<>();
        cmbDeleteName.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteInputPanel.add(cmbDeleteName, gbc);

        // Delete Button
        gbc.gridx = 2;
        gbc.weightx = 0;
        btnDelete = createButton("Delete", new Color(220, 20, 60));
        btnDelete.addActionListener(e -> deleteFruit());
        deleteInputPanel.add(btnDelete, gbc);

        deleteSection.add(deleteInputPanel, BorderLayout.CENTER);
        add(deleteSection);

        // Load initial data
        loadFruitComboBoxes();
    }

    /**
     * Create a section panel with title
     */
    private JPanel createSection(String title) {
        JPanel section = new JPanel(new BorderLayout(10, 10));
        section.setBackground(new Color(220, 220, 220));
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 0, 205)); // Blue
        section.add(lblTitle, BorderLayout.NORTH);

        return section;
    }

    /**
     * Create styled button
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Load fruits into combo boxes
     */
    public void loadFruitComboBoxes() {
        cmbUpdateName.removeAllItems();
        cmbDeleteName.removeAllItems();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT name FROM fruits ORDER BY name";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                cmbUpdateName.addItem(name);
                cmbDeleteName.addItem(name);
            }

            rs.close();
            pstmt.close();

            // Load details for first item
            if (cmbUpdateName.getItemCount() > 0) {
                loadFruitDetails();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load fruit details when selected in update combo box
     */
    private void loadFruitDetails() {
        String selectedFruit = (String) cmbUpdateName.getSelectedItem();
        if (selectedFruit == null)
            return;

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT price, amount FROM fruits WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, selectedFruit);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                txtUpdatePrice.setText(String.valueOf(rs.getDouble("price")));
                txtUpdateAmount.setText(String.valueOf(rs.getDouble("amount")));
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add new fruit to database
     */
    private void addFruit() {
        String name = txtAddName.getText().trim();
        String priceStr = txtAddPrice.getText().trim();
        String amountStr = txtAddAmount.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            double amount = Double.parseDouble(amountStr);

            if (price <= 0 || amount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Price and amount must be greater than zero!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO fruits (name, price, amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setDouble(3, amount);

            pstmt.executeUpdate();
            pstmt.close();

            JOptionPane.showMessageDialog(this,
                    "Fruit Added Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear fields and refresh
            txtAddName.setText("");
            txtAddPrice.setText("");
            txtAddAmount.setText("");
            loadFruitComboBoxes();
            refreshOtherPanels();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Number Format!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                JOptionPane.showMessageDialog(this,
                        "Fruit already exists!",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Update existing fruit
     */
    private void updateFruit() {
        String name = (String) cmbUpdateName.getSelectedItem();
        String priceStr = txtUpdatePrice.getText().trim();
        String amountStr = txtUpdateAmount.getText().trim();

        if (name == null || priceStr.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a fruit and fill all fields!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            double amount = Double.parseDouble(amountStr);

            if (price <= 0 || amount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Price and amount must be greater than zero!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE fruits SET price = ?, amount = ? WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, price);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, name);

            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        "Fruit Updated Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshOtherPanels();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Fruit not found!",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Number Format!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete fruit from database
     */
    private void deleteFruit() {
        String name = (String) cmbDeleteName.getSelectedItem();
        if (name == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a fruit to delete!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmation dialog
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete '" + name + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DatabaseConnection.getConnection();
                String query = "DELETE FROM fruits WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Fruit Deleted Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadFruitComboBoxes();
                    refreshOtherPanels();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Fruit not found!",
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Refresh other panels (inventory and billing)
     */
    private void refreshOtherPanels() {
        // Get parent frame and refresh other panels
        Container parent = getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }

        if (parent instanceof MainFrame) {
            ((MainFrame) parent).refreshPanels();
        }
    }
}
