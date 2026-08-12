package com.matuma.oibsip.reservation;

import javax.swing.*;
import java.awt.*;

/**
 * Main dashboard shown after successful login.
 * Provides navigation to the booking form and the cancellation screen.
 */
public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Online Reservation System — Dashboard");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("What would you like to do?", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton bookButton = new JButton("Book a Reservation");
        JButton cancelButton = new JButton("Cancel a Reservation");

        bookButton.addActionListener(e -> {
            new BookingFrame().setVisible(true);
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            new CancellationFrame().setVisible(true);
            this.dispose();
        });

        panel.add(titleLabel);
        panel.add(bookButton);
        panel.add(cancelButton);

        add(panel);
    }
}