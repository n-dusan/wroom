from flask import Flask
from flask import Response
import pika
import os
import json
import requests

app = Flask(__name__)

host_name = os.getenv("RMQ_HOST")

url = 'http://localhost:8073/gps'

@app.route('/')
def index():
    if host_name:
        return f"Hello from {host_name} running in a Docker container!"
    return 'Couldn\'t grab host_name'


@app.route('/generate/<id>')
def generate_jwt(id):
    request = requests.get(url)
    print(request.text)
    return Response(
        request.text,
        status=request.status_code,
        content_type=request.headers['content-type'],
        mimetype='application/json'
    )

@app.route('/test/<cmd>')
def add(cmd):

    #send a message
    message = { 'token': '322323', 'name': cmd}

    connection = pika.BlockingConnection(pika.ConnectionParameters(host=host_name))
    channel = connection.channel()
    channel.queue_declare(queue='gps', durable=False)
    channel.basic_publish(
        exchange='gps_tx',
        routing_key='gps_key',
        body=json.dumps(message),
        properties=pika.BasicProperties(
            content_type="application/json",
            headers={'__TypeId__':
                'com.wroom.vehicleservice.consumer.GpsCoordinates',
            },
            content_encoding= 'UTF-8',
            delivery_mode=2,  # make message persistent
        ))
    connection.close()
    return " [x] Sent: %s" % message


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')