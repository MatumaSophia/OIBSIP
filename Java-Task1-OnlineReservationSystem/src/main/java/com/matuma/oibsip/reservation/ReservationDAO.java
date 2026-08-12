package com.matuma.oibsip.reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;


public class ReservationDAO {

    public String bookReservation(String passengerName, String trainNumber, String trainName,
                                  String classType, LocalDate journeyDate,
                                  String sourceStation, String destinationStation) {

        String pnr = generateUniquePnr();

        String sql = "INSERT INTO reservations " +
                "(pnr, passenger_name, train_number, train_name, class_type, journey_date, source_station, destination_station) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pnr);
            stmt.setString(2, passengerName);
            stmt.setString(3, trainNumber);
            stmt.setString(4, trainName);
            stmt.setString(5, classType);
            stmt.setDate(6, Date.valueOf(journeyDate));
            stmt.setString(7, sourceStation);
            stmt.setString(8, destinationStation);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0 ? pnr : null;

        } catch (SQLException e) {
            System.out.println("Booking failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches a reservation by its PNR. Returns null if not found.
     */
    public Reservation getReservationByPnr(String pnr) {
        String sql = "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reservation(
                        rs.getString("pnr"),
                        rs.getString("passenger_name"),
                        rs.getString("train_number"),
                        rs.getString("train_name"),
                        rs.getString("class_type"),
                        rs.getDate("journey_date").toLocalDate(),
                        rs.getString("source_station"),
                        rs.getString("destination_station")
                );
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Lookup failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a reservation by PNR. Returns true if a row was removed.
     */
    public boolean cancelReservation(String pnr) {
        String sql = "DELETE FROM reservations WHERE pnr = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pnr);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates an 8-character alphanumeric PNR and checks the database
     * to make sure it isn't already in use, retrying if necessary.
     */
    private String generateUniquePnr() {
        String pnr;
        do {
            pnr = generateRandomPnr();
        } while (pnrExists(pnr));
        return pnr;
    }

    private String generateRandomPnr() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private boolean pnrExists(String pnr) {
        return getReservationByPnr(pnr) != null;
    }
}

