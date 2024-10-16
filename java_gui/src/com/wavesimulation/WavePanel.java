package com.wavesimulation;

import javax.swing.*;
import java.awt.*;

public class WavePanel extends JPanel {
    private JLabel locationLabel; // Combined label for latitude and longitude
    private JLabel waveHeightLabel;
    private JLabel waveDirectionLabel;
    private JLabel oceanCurrentVelocityLabel;
    private JLabel oceanCurrentDirectionLabel;
    private JLabel timestampLabel; // Label for timestamp

    public WavePanel() {
        // Set layout for the panel
        setLayout(new GridLayout(6, 1)); // Adjusted for 6 rows

        // Create labels to display all relevant information
        locationLabel = new JLabel("Location: Waiting for data...", SwingConstants.CENTER);
        waveHeightLabel = new JLabel("Wave Height: Waiting for data...", SwingConstants.CENTER);
        waveDirectionLabel = new JLabel("Wave Direction: Waiting for data...", SwingConstants.CENTER);
        oceanCurrentVelocityLabel = new JLabel("Ocean Current Velocity: Waiting for data...", SwingConstants.CENTER);
        oceanCurrentDirectionLabel = new JLabel("Ocean Current Direction: Waiting for data...", SwingConstants.CENTER);
        timestampLabel = new JLabel("Timestamp: Waiting for data...", SwingConstants.CENTER); // Initialize timestamp label

        // Set font for labels
        Font font = new Font("Arial", Font.PLAIN, 20);
        locationLabel.setFont(font);
        waveHeightLabel.setFont(font);
        waveDirectionLabel.setFont(font);
        oceanCurrentVelocityLabel.setFont(font);
        oceanCurrentDirectionLabel.setFont(font);
        timestampLabel.setFont(font); // Set font for timestamp

        // Add labels to the panel
        add(locationLabel);
        add(waveHeightLabel);
        add(waveDirectionLabel);
        add(oceanCurrentVelocityLabel);
        add(oceanCurrentDirectionLabel);
        add(timestampLabel); // Add timestamp label to panel
    }

    // Method to update all relevant data on the GUI
    public void updateWaveData(WaveData waveData) {
        // Update location label with formatted latitude and longitude
        String location = String.format("Location: %s, %s",
                convertToDMS(waveData.latitude, "lat"),
                convertToDMS(waveData.longitude, "lon"));
        locationLabel.setText(location);

        // Update other labels
        waveHeightLabel.setText(String.format("Wave Height: %.2f m", waveData.waveHeight));
        waveDirectionLabel.setText(String.format("Wave Direction: %.2f °", waveData.waveDirection));
        oceanCurrentVelocityLabel.setText(String.format("Ocean Current Velocity: %.2f km/h", waveData.oceanCurrentVelocity));
        oceanCurrentDirectionLabel.setText(String.format("Ocean Current Direction: %.2f °", waveData.oceanCurrentDirection));
        timestampLabel.setText(String.format("Timestamp: %s", waveData.timestamp)); // Update timestamp label text
    }

    // Helper method to convert latitude/longitude to Degrees, Minutes, Seconds (DMS) format
    private String convertToDMS(double decimalDegree, String type) {
        String direction;
        if (type.equals("lat")) {
            direction = (decimalDegree >= 0) ? "N" : "S";  // Determine North or South
        } else {
            direction = (decimalDegree >= 0) ? "E" : "W";  // Determine East or West
        }
        decimalDegree = Math.abs(decimalDegree);

        int degrees = (int) decimalDegree;
        double minutesDecimal = (decimalDegree - degrees) * 60;
        int minutes = (int) minutesDecimal;
        double seconds = (minutesDecimal - minutes) * 60;

        // Format as degrees, minutes, seconds with the direction
        return String.format("%d° %d' %.2f\" %s", degrees, minutes, seconds, direction);
    }
}
