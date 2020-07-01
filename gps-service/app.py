from flask import Flask
from flask import Response
import pika
import os
import json
import requests
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry
import threading
import time
import random

app = Flask(__name__)

host_name = os.getenv("RMQ_HOST")

url = 'http://192.168.0.15:8073/gps'
chosen_city = None
token = ''

#china, arizona and italy
coordinates = {
    'Wuhan': [
        [30.6178, 114.251], [30.6167, 114.251], [30.6141, 114.253], [30.6114, 114.254], [30.6098, 114.254], [30.6102, 114.257], [30.6109, 114.261], [30.6117, 114.262], [30.6126, 114.261], [30.6141, 114.259], [30.6161, 114.259], [30.6182, 114.258], [30.6184, 114.256], [30.6180, 114.255],  [30.6174, 114.253], [30.6178, 114.251]
    ],
    'Phoenix': [
        [33.5751, -112.065],[33.5742, -112.065],[33.5733, -112.065],[33.5720, -112.065],[33.5713, -112.065],[33.5714, -112.066],[33.5713, -112.068],[33.5714, -112.069],[33.5713, -112.070],[33.5713, -112.071],[33.5714, -112.072],[33.5714, -112.073],[33.5717, -112.074],[33.5727, -112.074],[33.5741, -112.074],[33.5750, -112.073],[33.5749, -112.071],[33.5750, -112.069],[33.5749, -112.068],[33.5750, -112.067]
    ],
    'Rome': [
        [41.9015, 12.4508],[41.9016, 12.4504],[41.9018, 12.4500],[41.9019, 12.4495],[41.9024, 12.4489],[41.9028, 12.4496],[41.9030, 12.4500],[41.9033, 12.4509],[41.9035, 12.4515],[41.9038, 12.4521],[41.9044, 12.4521],[41.9051, 12.4520],[41.9058, 12.4518],[41.9062, 12.4517],[41.9065, 12.4524],[41.9068, 12.4538],[41.9069, 12.4547],[41.9064, 12.4552],[41.9060, 12.4553],[41.9056, 12.4555],[41.9061, 12.4556],[41.9069, 12.4554],[41.9075, 12.4555]
    ]
}

cities = {
    0: 'Wuhan',
    1: 'Phoenix',
    2: 'Rome'
}

# generates coordinates every x seconds
def interval_coordinates():
    idx = 0
    while True:
        time.sleep(5)
        global coordinates
        try:
            #todo change 0 to chosen_city
            coords = coordinates.get(cities[chosen_city])[idx]
            idx = idx + 1
            app.logger.info('index coords %s' % coords)
            app.logger.info('index is %s' % index)
        except IndexError as e:
            idx = 0
            coords = coordinates.get(cities[chosen_city])[idx]
        app.logger.info('Coordinates %s' % coords)
        send_coordinates(coords)

thread = threading.Thread(name='interval_coordinates', target=interval_coordinates)
thread.setDaemon(True)

@app.route('/')
def index():
    if host_name:
        return f"Hello from {host_name} flask application"
    return 'Couldn\'t grab host_name'


@app.route('/kill')
def kill():
    thread.stop()
    return 'OK'


@app.route('/generate/<id>')
def generate_jwt(id):

    headers = {'content-type': 'application/json; charset=UTF-8'}
    session = requests.Session()
    retry = Retry(connect=3, backoff_factor=0.5)
    adapter = HTTPAdapter(max_retries=retry)
    session.mount('http://', adapter)
    session.mount('https://', adapter)
    data = '''{
                
              }
            '''
    request = session.post(url + '/' + id, headers = headers, json = data)

    global token
    token = request.text

    #generate a random number for which the city will have coordinates sent.
    global chosen_city
    chosen_city = random.randint(0, 2)

    #start daemon
    thread.start()

    return Response(
        request.text,
        status=request.status_code,
        content_type=request.headers['content-type'],
        mimetype='application/json'
    )

def send_coordinates(coords):
    message = {
        'token': token,
        'latitude': coords[0],
        'longitude': coords[1]
    }

    app.logger.info('Message is %s' % message)
    connection = pika.BlockingConnection(pika.ConnectionParameters(host=host_name))
    channel = connection.channel()
    channel.queue_declare(queue='gps', durable=False)
    channel.basic_publish(
        exchange='gps_tx',
        routing_key='gps_key',
        body=json.dumps(message),
        properties=pika.BasicProperties(
            content_type="application/json",
            headers={'__TypeId__': 'com.wroom.vehicleservice.consumer.GpsCoordinates',
                     },
            content_encoding= 'UTF-8',
            delivery_mode=2,  # make message persistent
        ))
    connection.close()

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')