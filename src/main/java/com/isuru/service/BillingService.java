package com.isuru.service;

import com.isuru.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingService {

    public record BillItemResult(double unitPrice, double totalPrice) {
    }

    private List<Double> itemTotals = new ArrayList<>();

    /**
     * Calculates the total price for a given fruit and amount, and adds it to the
     * current bill.
     * Fetches the price from the database.
     *
     * @param fruitName The name of the fruit.
     * @param amount    The amount in kg.
     * @return The result containing unit price and total price.
     * @throws SQLException If a database access error occurs.
     */
    public BillItemResult addItem(String fruitName, double amount) throws SQLException {
        BillItemResult result = calculateItemResult(fruitName, amount);
        itemTotals.add(result.totalPrice());
        return result;
    }

    /**
     * Calculates grand total of all items added to the bill.
     * 
     * @return Grand total price.
     */
    public double getGrandTotal() {
        return itemTotals.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Clears the current bill.
     */
    public void clearBill() {
        itemTotals.clear();
    }

    /**
     * Internal helper to fetch price and calculate total for single item.
     */
    private BillItemResult calculateItemResult(String fruitName, double amount) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT price FROM fruits WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, fruitName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double price = rs.getDouble("price");
                    return new BillItemResult(price, price * amount);
                } else {
                    throw new IllegalArgumentException("Fruit not found: " + fruitName);
                }
            }
        }
    }

    // Kept for backward compatibility if needed, but updated to use new internal
    // object
    public double calculateTotal(String fruitName, double amount) throws SQLException {
        return calculateItemResult(fruitName, amount).totalPrice();
    }
}
