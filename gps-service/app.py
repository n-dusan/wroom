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

token = ''
coordinates = []

# generates coordinates every 3 seconds
def interval_coordinates():
    while True:
        time.sleep(3)
        global coordinates
        coordinates = [ random.randint(0,100), random.randint(0,100) ]
        app.logger.info('Coordinates %s' % coordinates)
        send_coordinates(coordinates)

thread = threading.Thread(name='interval_coordinates', target=interval_coordinates)
thread.setDaemon(True)

@app.route('/')
def index():
    if host_name:
        return f"Hello from {host_name} flask application"
    return 'Couldn\'t grab host_name'


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
    app.logger.info('and JWT is %s' % token)
    #start daemon
    thread.start()

    return Response(
        request.text,
        status=request.status_code,
        content_type=request.headers['content-type'],
        mimetype='application/json'
    )

def send_coordinates(coords):
    #send a message
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

@app.route('/test/<cmd>')
def add(cmd):

    #send a message
    message = {
        'token': token,
        'latitude': '532',
        'longitude': '322'
    }
    app.logger.info('host name %s' % host_name)
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
    return " [x] Sent: %s" % message


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')