package com.isuru;

import com.isuru.gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set look and feel to system default
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}