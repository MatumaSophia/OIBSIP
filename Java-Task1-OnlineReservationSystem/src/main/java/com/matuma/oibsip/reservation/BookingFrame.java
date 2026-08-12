package com.matuma.oibsip.reservation;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Reservation form — collects passenger and journey details,
 * validates input, and books the reservation via ReservationDAO.
 */
public class BookingFrame extends JFrame {

    private final JTextField nameField, trainNumberField, trainNameField, dateField,
            sourceField, destinationField;
    private final JComboBox<String> classBox;
    private final ReservationDAO reservationDAO;

    public BookingFrame() {
        reservationDAO = new ReservationDAO();

        setTitle("Book a Reservation");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        nameField = addFormRow(panel, gbc, row++, "Passenger Name:");
        trainNumberField = addFormRow(panel, gbc, row++, "Train Number:");
        trainNameField = addFormRow(panel, gbc, row++, "Train Name:");

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Class Type:"), gbc);
        classBox = new JComboBox<>(new String[]{"Sleeper", "AC 3 Tier", "AC 2 Tier", "First Class"});
        gbc.gridx = 1;
        panel.add(classBox, gbc);
        row++;

        dateField = addFormRow(panel, gbc, row++, "Journey Date (yyyy-mm-dd):");
        sourceField = addFormRow(panel, gbc, row++, "Source Station:");
        destinationField = addFormRow(panel, gbc, row++, "Destination Station:");

        JButton bookButton = new JButton("Book Reservation");
        JButton backButton = new JButton("Back to Dashboard");

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(bookButton, gbc);

        row++;
        gbc.gridy = row;
        panel.add(backButton, gbc);

        bookButton.addActionListener(e -> handleBooking());
        backButton.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            this.dispose();
        });

        add(panel);
    }

    private JTextField addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        JTextField field = new JTextField(15);
        gbc.gridx = 1;
        panel.add(field, gbc);
        return field;
    }

    private void handleBooking() {
        String name = nameField.getText().trim();
        String trainNumber = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classBox.getSelectedItem();
        String dateText = dateField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (name.isEmpty() || trainNumber.isEmpty() || trainName.isEmpty() ||
                dateText.isEmpty() || source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!trainNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Train number must be numeric.",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate journeyDate;
        try {
            journeyDate = LocalDate.parse(dateText, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date must be in yyyy-mm-dd format.",
                    "Invalid Date", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String pnr = reservationDAO.bookReservation(
                name, trainNumber, trainName, classType, journeyDate, source, destination
        );

        if (pnr != null) {
            JOptionPane.showMessageDialog(this,
                    "Booking confirmed!\n\n" +
                            "PNR: " + pnr + "\n" +
                            "Passenger: " + name + "\n" +
                            "Train: " + trainNumber + " - " + trainName + "\n" +
                            "Class: " + classType + "\n" +
                            "Date: " + journeyDate + "\n" +
                            "Route: " + source + " → " + destination,
                    "Reservation Confirmed", JOptionPane.INFORMATION_MESSAGE);

            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
