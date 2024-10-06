from quixstreams import Application
import json

app = Application(
    broker_address="localhost:9092",
    loglevel="DEBUG",
    consumer_group="maritime_data_reader",
)

with app.get_consumer() as consumer:
    consumer.subscribe(["maritime_data_for_simulation"])

    while True:
        msg = consumer.poll(1)
        if msg is None:
            print("Waiting...")
        elif msg.error() is not None:
            raise Exception(msg.error())
        else:
            key = msg.key().decode("utf8")
            value = json.loads(msg.value())
            offset = msg.offset()
            print(f"{offset} {key} {value}")
