package com.wavesimulation;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a WavePanel instance
            WavePanel wavePanel = new WavePanel();

            // Create and start the Kafka consumer in a separate thread
            WaveHeightConsumer consumer = new WaveHeightConsumer(wavePanel);
            new Thread(consumer::start).start();

            // Create and set up the JFrame
            JFrame frame = new JFrame("Wave Height Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(wavePanel);
            frame.setSize(400, 300); // Set the frame size
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true); // Make the frame visible
        });
    }
}

