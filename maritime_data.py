import requests
import json
from quixstreams import Application

response = requests.get(
    "https://marine-api.open-meteo.com/v1/marine",
    params={
        "latitude": "54.544587",
        "longitude": "10.227487",
        "current": "wave_height,wave_direction,ocean_current_velocity,ocean_current_direction",
    },
)

maritime_data = response.json()

app = Application(
    broker_address="localhost:9092",
    loglevel="DEBUG",
)

with app.get_producer() as producer:
    producer.produce(
        topic="maritime_data_test",
        key="Gedser",
        value=json.dumps(maritime_data),
    )
