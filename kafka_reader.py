from quixstreams import Application
import json


def print_sea_info(msg):
    # extract relevant info
    current_data = msg["current"]
    latitude = msg["latitude"]
    longitude = msg["longitude"]
    wave_height = current_data.get("wave_height", "N/A")
    wave_direction = current_data.get("wave_direction", "N/A")
    ocean_current_velocity = current_data.get("ocean_current_velocity", "N/A")
    ocean_current_direction = current_data.get("ocean_current_direction", "N/A")

    # print current conditions
    print(f"Location: {latitude}N,{longitude}E")
    print(f"Wave Height: {wave_height} m")
    print(f"Wave Direction: {wave_direction}°")
    print(f"Ocean Current Velocity: {ocean_current_velocity} km/h")
    print(f"Ocean Current Direction: {ocean_current_direction}°")


app = Application(
    broker_address="localhost:9092",
    loglevel="DEBUG",
    consumer_group="maritime_data_reader",
    auto_offset_reset="latest",
)


def main():

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
                print(f"Message Offset: {offset}, Key: {key}")
                print_sea_info(value)
                consumer.store_offsets(msg)


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass
