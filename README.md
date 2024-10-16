# Maritime Data Stream Project

This project streams wave height and related maritime data using a Kafka-based streaming system. The data is produced by a **Python Kafka producer** and consumed by a **Java GUI**, which currently displays the wave height, wave direction, and other maritime information.
Ideally in the future I would like to graphically display the wave in the GUI.

## Table of Contents
- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
  - [Kafka and Docker](#kafka-and-docker-setup)
  - [Python Producer](#python-producer-setup)
  - [Java GUI Consumer](#java-gui-setup)
- [How to Run](#how-to-run)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Overview
The **Wave Simulation Project** is designed to stream maritime data such as wave height, wave direction, and other related metrics. This data is sent to a Kafka topic by a Python-based producer and visualized in real-time by a Java GUI application that consumes the Kafka topic.

## Technologies Used
- **Apache Kafka**: Distributed streaming platform.
- **Python**: Used for generating and producing maritime data.
- **Java**: For creating the GUI to consume and display the Kafka messages.
- **Gson**: Library used in Java for parsing JSON data.
  
## Project Structure

```bash
WaveSimulation/
├── java_gui/
│   ├── src/
│   │   └── com/
│   │       └── wavesimulation/
│   │           ├── AppLauncher.java        # Java GUI launcher
│   │           ├── WaveHeightConsumer.java # Kafka consumer for the Java GUI
│   │           ├── WavePanel.java          # GUI panel to display wave data
│   ├── images/                             # Folder containing screenshots for the Java GUI
│   │   └── wave_simulation_screenshot.png  # Screenshot of the wave simulation GUI
│   └── lib/                                # External JAR libraries for Kafka client and Gson
│
├── python_backend/
│   ├── maritime_data.py                    # Python Kafka producer for maritime data
│   └── kafka_reader.py                     # Python Kafka consumer/reader script
│
├── docker-compose.yml                      # Kafka and Zookeeper Docker setup
├── .gitignore                              # Gitignore file to exclude unnecessary files
└── README.md                               # Project README file

## Setup and Installation

### Kafka and Docker Setup
1. Ensure you have Docker installed.
2. Use the provided `docker-compose.yml` file to set up Kafka and Zookeeper:
   ```bash
   docker-compose up -d
   ```
3. This will launch Kafka and Zookeeper locally. Kafka will be available on `localhost:9092`.

### Python Producer Setup
1. Install the required Python libraries by running:
   ```bash
   pip install kafka-python quixstreams
   ```
2. Navigate to the `producer/` directory and run the Python Kafka producer:
   ```bash
   python maritime_data.py
   ```

### Java GUI Setup
1. Install a Java IDE like IntelliJ IDEA.
2. Include the required JAR libraries for Kafka and Gson in the `lib/` folder:
   - Kafka client JARs
   - Gson JAR
3. Compile and run the Java GUI by launching the `AppLauncher.java` file. This will connect to the Kafka topic and visualize the wave data.

## How to Run
1. **Start Kafka**:
   - Make sure Kafka is running by executing:
     ```bash
     docker-compose up -d
     ```
2. **Run the Python Producer**:
   - Navigate to the `producer/` folder and execute:
     ```bash
     python maritime_data.py
     ```
3. **Run the Java GUI**:
   - Open the Java project in IntelliJ or your preferred IDE.
   - Run the `AppLauncher.java` file.
   - The GUI will display wave height, wave direction, and other maritime data.

## Screenshots
![Maritime Data Waiting for New Data Display](java_gui/src/images/waitig_for_data.png)

![Live Maritime Data Display](java_gui/src/images/incomin_data.png)

## Contributing
Feel free to contribute to the project by submitting issues or pull requests.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
