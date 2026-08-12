package com.matuma.oibsip.reservation;

import javax.swing.*;
import java.awt.*;

/**
 * Cancellation screen — looks up a reservation by PNR, displays the
 * booking details, and removes it from the database on confirmation.
 */
public class CancellationFrame extends JFrame {

    private final JTextField pnrField;
    private final JTextArea detailsArea;
    private final ReservationDAO reservationDAO;
    private Reservation currentReservation;

    public CancellationFrame() {
        reservationDAO = new ReservationDAO();

        setTitle("Cancel a Reservation");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Enter PNR:"));
        pnrField = new JTextField(15);
        topPanel.add(pnrField);
        JButton fetchButton = new JButton("Fetch Booking");
        topPanel.add(fetchButton);

        detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton cancelBookingButton = new JButton("Confirm Cancellation");
        JButton backButton = new JButton("Back to Dashboard");
        bottomPanel.add(cancelBookingButton);
        bottomPanel.add(backButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> handleFetch());
        cancelBookingButton.addActionListener(e -> handleCancellation());
        backButton.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            this.dispose();
        });

        add(panel);
    }

    private void handleFetch() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentReservation = reservationDAO.getReservationByPnr(pnr);

        if (currentReservation != null) {
            detailsArea.setText(currentReservation.toString());
        } else {
            detailsArea.setText("");
            JOptionPane.showMessageDialog(this, "No booking found for PNR: " + pnr,
                    "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleCancellation() {
        if (currentReservation == null) {
            JOptionPane.showMessageDialog(this, "Please fetch a valid booking first.",
                    "No Booking Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?\n\n" + currentReservation,
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = reservationDAO.cancelReservation(currentReservation.getPnr());

            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                detailsArea.setText("");
                pnrField.setText("");
                currentReservation = null;
            } else {
                JOptionPane.showMessageDialog(this, "Cancellation failed. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}