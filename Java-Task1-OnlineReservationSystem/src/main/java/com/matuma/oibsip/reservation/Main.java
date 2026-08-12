package com.matuma.oibsip.reservation;

import javax.swing.*;

/**
 * Entry point — launches the login screen.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}