package com.matuma.oibsip.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles the JDBC connection to the MySQL/MariaDB reservation system database.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/reservation_system";
    private static final String USER = "reservation_app";
    private static final String PASSWORD = "OibsipTask1!2026";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Quick standalone test to check the database connection
     */
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to reservation_system successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}