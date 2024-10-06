import requests
import json
from quixstreams import Application
import logging
import time


# Pull maritime data from lat long from Ferry route Rostock-Gedser in the Baltic Sea
def get_maritime_data():
    response = requests.get(
        "https://marine-api.open-meteo.com/v1/marine",
        params={
            "latitude": "54.544587",
            "longitude": "10.227487",
            "current": "wave_height,wave_direction,ocean_current_velocity,ocean_current_direction",
        },
    )

    return response.json()


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
            sleeptime = 30
            logging.info("Produced... Sleeping for... {sleeptime} seconds")
            time.sleep(sleeptime)


if __name__ == "__main__":
    logging.basicConfig(level="DEBUG")
    main()
