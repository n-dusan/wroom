from flask import Flask
import pika
import os
import json

app = Flask(__name__)

host_name = os.getenv("RMQ_HOST")

@app.route('/')
def index():
    if host_name:
        return f"Hello from {host_name} running in a Docker container!"
    return 'Couldn\'t grab host_name'


@app.route('/test/<cmd>')
def add(cmd):

    #send a message
    message = { 'token': 1, 'name': cmd}

    connection = pika.BlockingConnection(pika.ConnectionParameters(host=host_name))
    channel = connection.channel()
    channel.queue_declare(queue='gps_queue', durable=True)
    channel.basic_publish(
        exchange='tips_tx',
        routing_key='gps_key',
        body=json.dumps(message),
        properties=pika.BasicProperties(
            delivery_mode=2,  # make message persistent
        ))
    connection.close()
    return " [x] Sent: %s" % message


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')