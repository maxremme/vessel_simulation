package com.wavesimulation;

import javax.swing.*;
import java.awt.*;

public class WavePanel extends JPanel {
    private JLabel latitudeLabel;
    private JLabel longitudeLabel;
    private JLabel waveHeightLabel;
    private JLabel waveDirectionLabel;
    private JLabel oceanCurrentVelocityLabel;
    private JLabel oceanCurrentDirectionLabel;
    private JLabel timestampLabel; // New label for timestamp

    public WavePanel() {
        // Set layout for the panel
        setLayout(new GridLayout(7, 1)); // Adjusted for 7 rows

        // Create labels to display all relevant information
        latitudeLabel = new JLabel("Latitude: Waiting for data...", SwingConstants.CENTER);
        longitudeLabel = new JLabel("Longitude: Waiting for data...", SwingConstants.CENTER);
        waveHeightLabel = new JLabel("Wave Height: Waiting for data...", SwingConstants.CENTER);
        waveDirectionLabel = new JLabel("Wave Direction: Waiting for data...", SwingConstants.CENTER);
        oceanCurrentVelocityLabel = new JLabel("Ocean Current Velocity: Waiting for data...", SwingConstants.CENTER);
        oceanCurrentDirectionLabel = new JLabel("Ocean Current Direction: Waiting for data...", SwingConstants.CENTER);
        timestampLabel = new JLabel("Timestamp: Waiting for data...", SwingConstants.CENTER); // Initialize timestamp label

        latitudeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        longitudeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        waveHeightLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        waveDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        oceanCurrentVelocityLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        oceanCurrentDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font for timestamp

        // Add labels to the panel
        add(latitudeLabel);
        add(longitudeLabel);
        add(waveHeightLabel);
        add(waveDirectionLabel);
        add(oceanCurrentVelocityLabel);
        add(oceanCurrentDirectionLabel);
        add(timestampLabel); // Add timestamp label to panel
    }

    // Method to update all relevant data on the GUI
    public void updateWaveData(WaveData waveData) {
        latitudeLabel.setText(String.format("Latitude: %.2f", waveData.latitude));
        longitudeLabel.setText(String.format("Longitude: %.2f", waveData.longitude));
        waveHeightLabel.setText(String.format("Wave Height: %.2f m", waveData.waveHeight));
        waveDirectionLabel.setText(String.format("Wave Direction: %.2f °", waveData.waveDirection));
        oceanCurrentVelocityLabel.setText(String.format("Ocean Current Velocity: %.2f km/h", waveData.oceanCurrentVelocity));
        oceanCurrentDirectionLabel.setText(String.format("Ocean Current Direction: %.2f °", waveData.oceanCurrentDirection));
        timestampLabel.setText(String.format("Timestamp: %s", waveData.timestamp)); // Update timestamp label text
    }
}
