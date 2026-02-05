package com.isuru.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;
    private static Properties properties = null;

    // Initialize database connection
    static {
        try {
            // Load configuration properties
            properties = new Properties();
            InputStream input = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                System.err.println("Unable to find config.properties");
            } else {
                properties.load(input);
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get singleton database connection
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password", "");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");

            // Initialize database schema if needed
            initializeDatabase();
        }
        return connection;
    }

    /**
     * Initialize database schema and sample data
     */
    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Create fruits table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS fruits (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) UNIQUE NOT NULL, " +
                    "price DECIMAL(10,2) NOT NULL, " +
                    "amount DECIMAL(10,2) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'fruits' ready.");

            // Insert sample data if table is empty
            String checkDataSQL = "SELECT COUNT(*) as count FROM fruits";
            var rs = stmt.executeQuery(checkDataSQL);
            rs.next();
            int count = rs.getInt("count");

            if (count == 0) {
                String insertSampleData = "INSERT INTO fruits (name, price, amount) VALUES " +
                        "('Apple', 150.00, 50.00), " +
                        "('Banana', 80.00, 100.00), " +
                        "('Orange', 120.00, 75.00), " +
                        "('Mango', 200.00, 30.00)";
                stmt.executeUpdate(insertSampleData);
                System.out.println("Sample data inserted.");
            }
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
