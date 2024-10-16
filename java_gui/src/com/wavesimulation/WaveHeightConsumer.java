package com.wavesimulation;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class WaveHeightConsumer {
    private final String topicName = "maritime_data_for_simulation"; // Kafka topic
    private final WavePanel wavePanel;  // Reference to the GUI panel
    private final Gson gson = new Gson();  // For parsing JSON

    public WaveHeightConsumer(WavePanel wavePanel) {
        this.wavePanel = wavePanel;  // Pass the GUI panel to update wave data
    }

    public void start() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Kafka server address
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wave-height-group");  // Consumer group ID
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topicName));  // Subscribe to the topic
            System.out.println("Waiting for messages...");

            // Poll and consume messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: key = %s, value = %s%n", record.key(), record.value());

                    // Extract wave data from message and update the GUI panel
                    WaveData waveData = extractWaveData(record.value());
                    wavePanel.updateWaveData(waveData);  // Update GUI with the new wave data
                }
            }
        }
    }

    // Parse and extract relevant data from the JSON message using Gson
    private WaveData extractWaveData(String messageValue) {
        try {
            // Parse JSON into a Java object
            MaritimeData maritimeData = gson.fromJson(messageValue, MaritimeData.class);
            String formattedTimestamp = maritimeData.current.time.replace("T", " "); // Remove 'T' from timestamp
            return new WaveData(
                    maritimeData.latitude,
                    maritimeData.longitude,
                    maritimeData.current.wave_height,
                    maritimeData.current.wave_direction,
                    maritimeData.current.ocean_current_velocity,
                    maritimeData.current.ocean_current_direction,
                    formattedTimestamp // Use formatted timestamp
            );  // Return all relevant data
        } catch (Exception e) {
            e.printStackTrace();  // Log parsing errors
            return new WaveData(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, "");  // Default values if parsing fails
        }
    }
}

// Helper classes to map the JSON data
class Current {
    double wave_height;            // The height of the waves
    double wave_direction;         // The direction of the waves
    double ocean_current_velocity;  // The velocity of ocean currents
    double ocean_current_direction; // The direction of ocean currents
    String time;                   // The timestamp
}

class MaritimeData {
    double latitude;   // Latitude
    double longitude;  // Longitude
    Current current;   // The current object that contains wave and current data
}

// Class to hold all relevant wave data
class WaveData {
    double latitude;
    double longitude;
    double waveHeight;
    double waveDirection;
    double oceanCurrentVelocity;
    double oceanCurrentDirection;
    String timestamp; // New field for timestamp

    WaveData(double latitude, double longitude, double waveHeight, double waveDirection,
             double oceanCurrentVelocity, double oceanCurrentDirection, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.waveHeight = waveHeight;
        this.waveDirection = waveDirection;
        this.oceanCurrentVelocity = oceanCurrentVelocity;
        this.oceanCurrentDirection = oceanCurrentDirection;
        this.timestamp = timestamp; // Initialize timestamp
    }
}
