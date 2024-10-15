import requests
import json
from quixstreams import Application
import logging
import time


# Pull maritime data from lat long from Ferry route Rostock-Gedser in the Baltic Sea
def get_maritime_data():

    # Gedser-Rostock ferry route points (interpolated
    # Start: (54.56, 11.93) - Gedser
    # (54.53, 11.94)
    # (54.51, 11.95)
    # (54.48, 11.95)
    # (54.46, 11.96)
    # (54.43, 11.97)
    # Near Middle: (54.41, 11.97)
    # (54.38, 11.99)
    # (54.35, 12.00)
    # (54.32, 12.01)
    # (54.30, 12.02)
    # Near End: (54.29, 12.02)
    # (54.26, 12.03)
    # (54.23, 12.04)
    # (54.20, 12.05)
    # End: (54.19, 12.09) - Rostock
    # ------ ------- ------ ------ ------ ----- ------ ------
    # openmeteo spatial resolution too low granularity to capture differences along the route
    response = requests.get(
        "https://marine-api.open-meteo.com/v1/marine",
        params={
            "latitude": "54.41",
            "longitude": "11.97",
            "current": "wave_height,wave_direction,ocean_current_velocity,ocean_current_direction",
        },
    )

    return response.json()


print(get_maritime_data())


# define broker and produce data
def main():
    app = Application(
        broker_address="localhost:9092",
        loglevel="DEBUG",
    )

    with app.get_producer() as producer:
        while True:
            maritime_data = get_maritime_data()
            logging.debug("Got maritime data: %s", maritime_data)
            producer.produce(
                topic="maritime_data_for_simulation",
                key="Gedser_Rostock_Ferry",
                value=json.dumps(maritime_data),
            )
            sleeptime = 10
            logging.info("Produced... Sleeping for 10 seconds")
            time.sleep(sleeptime)


if __name__ == "__main__":
    logging.basicConfig(level="DEBUG")
    main()
