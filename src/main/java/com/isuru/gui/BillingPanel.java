package com.isuru.gui;

import com.isuru.service.EmailService;
import com.isuru.service.BillingService;
import com.isuru.util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPanel extends JPanel {
    private JComboBox<String> cmbFruitSelect;
    private JTextField txtAmount;
    private JTextArea txtBill;
    private JButton btnAdd;
    private JButton btnCalculate;
    private JButton btnClear;
    private JTextField txtEmail;
    private JButton btnSendEmail;

    private BillingService billingService;

    public BillingPanel() {
        billingService = new BillingService();

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main panel with vertical box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // ===== Add Fruits Section =====
        JPanel addFruitsPanel = new JPanel();
        addFruitsPanel.setLayout(new BorderLayout(10, 10));
        addFruitsPanel.setBackground(Color.WHITE);
        addFruitsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblAddTitle = new JLabel("Add fruits", SwingConstants.CENTER);
        lblAddTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblAddTitle.setForeground(new Color(0, 51, 153)); // Dark blue
        addFruitsPanel.add(lblAddTitle, BorderLayout.NORTH);

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Select fruit
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblSelectFruit = new JLabel("Select fruit:");
        lblSelectFruit.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblSelectFruit, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbFruitSelect = new JComboBox<>();
        cmbFruitSelect.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbFruitSelect.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(cmbFruitSelect, gbc);

        // Enter amount
        gbc.gridx = 2;
        gbc.weightx = 0;
        JLabel lblAmount = new JLabel("Enter amount:");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblAmount, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        txtAmount = new JTextField(15);
        txtAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        txtAmount.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(txtAmount, gbc);

        // Add button
        gbc.gridx = 4;
        gbc.weightx = 0;
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(124, 205, 0)); // Lime green
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(100, 35));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.addActionListener(e -> addItem());
        inputPanel.add(btnAdd, gbc);

        // Calculate button
        gbc.gridx = 4;
        gbc.gridy = 1;
        btnCalculate = new JButton("Calculate");
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalculate.setBackground(new Color(255, 152, 0)); // Orange
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setPreferredSize(new Dimension(100, 35));
        btnCalculate.setFocusPainted(false);
        btnCalculate.setBorderPainted(false);
        btnCalculate.addActionListener(e -> showGrandTotal());
        inputPanel.add(btnCalculate, gbc);

        addFruitsPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(addFruitsPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== Bill Section =====
        JPanel billPanel = new JPanel(new BorderLayout(10, 10));
        billPanel.setBackground(Color.WHITE);
        billPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblBillTitle = new JLabel("Bill", SwingConstants.CENTER);
        lblBillTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblBillTitle.setForeground(new Color(0, 51, 153)); // Dark blue
        billPanel.add(lblBillTitle, BorderLayout.NORTH);

        // Bill text area
        txtBill = new JTextArea(12, 50);
        txtBill.setFont(new Font("Courier New", Font.PLAIN, 13));
        txtBill.setEditable(false);
        txtBill.setBackground(Color.WHITE);
        txtBill.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JScrollPane scrollPane = new JScrollPane(txtBill);
        billPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(billPanel);

        add(mainPanel, BorderLayout.CENTER);

        // ===== Clear Button =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.setBackground(new Color(46, 125, 50)); // Dark green
        btnClear.setForeground(Color.WHITE);
        btnClear.setPreferredSize(new Dimension(120, 40));
        btnClear.setFocusPainted(false);
        btnClear.setBorderPainted(false);
        btnClear.addActionListener(e -> clearBill());

        bottomPanel.add(btnClear);

        // ===== Email Section =====
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.setBackground(Color.WHITE);
        emailPanel.setBorder(BorderFactory.createTitledBorder("Email Bill"));

        JLabel lblEmail = new JLabel("Customer Email: ");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        emailPanel.add(lblEmail);

        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        emailPanel.add(txtEmail);

        btnSendEmail = new JButton("Send Bill");
        btnSendEmail.setFont(new Font("Arial", Font.BOLD, 14));
        btnSendEmail.setBackground(new Color(33, 150, 243)); // Blue
        btnSendEmail.setForeground(Color.WHITE);
        btnSendEmail.setFocusPainted(false);
        btnSendEmail.setBorderPainted(false);
        btnSendEmail.addActionListener(e -> sendEmail());
        emailPanel.add(btnSendEmail);

        mainPanel.add(emailPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load fruits into combo box
        loadFruitComboBox();
    }

    /**
     * Load fruits from database into combo box
     */
    public void loadFruitComboBox() {
        cmbFruitSelect.removeAllItems();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT name FROM fruits ORDER BY name";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cmbFruitSelect.addItem(rs.getString("name"));
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
     * Calculate and add to bill
     */
    /**
     * Add fruit to bill and service
     */
    private void addItem() {
        String selectedFruit = (String) cmbFruitSelect.getSelectedItem();
        String amountStr = txtAmount.getText().trim();

        // Validation
        if (selectedFruit == null || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a fruit and enter amount!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Amount must be greater than zero!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Calculate & Add using Service
            BillingService.BillItemResult result = billingService.addItem(selectedFruit, amount);

            // Add to bill UI
            addToBill(selectedFruit, amount, result.unitPrice(), result.totalPrice());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Number Format!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Show grand total
     */
    private void showGrandTotal() {
        double grandTotal = billingService.getGrandTotal();
        txtBill.append("========================================\n");
        txtBill.append(String.format("GRAND TOTAL: Rs. %.2f%n", grandTotal));
        txtBill.append("========================================\n\n");
    }

    /**
     * Add item to bill text area
     */
    private void addToBill(String fruit, double amount, double unitPrice, double totalPrice) {
        String billEntry = String.format("Fruit:       %s%n", fruit);
        billEntry += String.format("Amount:      %.2f kg%n", amount);
        billEntry += String.format("Price:       Rs. %.2f%n", unitPrice);
        billEntry += String.format("Total Price: Rs. %.2f%n", totalPrice);
        billEntry += "----------------------------------------\n";

        txtBill.append(billEntry);

        // Clear amount field for next entry
        txtAmount.setText("");
    }

    /**
     * Clear the bill
     */
    private void clearBill() {
        txtBill.setText("");
        txtAmount.setText("");
        cmbFruitSelect.setSelectedIndex(0);
        billingService.clearBill();
        if (txtEmail != null)
            txtEmail.setText("");
    }

    /**
     * Send bill via email
     */
    private void sendEmail() {
        String email = txtEmail.getText().trim();
        String billContent = txtBill.getText();

        // Validation
        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address!",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (billContent.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "The bill is empty. Please add items to the bill first.",
                    "Empty Bill",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Send Email
        try {
            EmailService emailService = new EmailService();
            emailService.sendEmail(email, "Fruit Shop Bill Result", billContent);

            JOptionPane.showMessageDialog(this,
                    "Email sent successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to send email: " + e.getMessage(),
                    "Email Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
