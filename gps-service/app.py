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
    # message = { 'token': '213321', 'name': cmd}


    message = { 'id': 1,
                'name': 'boy',
                'surname': 'test',
                'email': 'aaa@maildrop.cc',
                'enabled': True,
                'nonLocked': True,
                'operation': 'ACTIVATE'}


    connection = pika.BlockingConnection(pika.ConnectionParameters(host=host_name))
    channel = connection.channel()
    channel.queue_declare(queue='auth', durable=False)
    channel.basic_publish(
        exchange='tips_tx',
        routing_key='auth_key',
        body=json.dumps(message),
        properties=pika.BasicProperties(
            content_type="application/json",
            headers={'__TypeId__':
                'com.wroom.searchservice.consumer.message.UserMessage',
            },
            content_encoding= 'UTF-8',
            delivery_mode=2,  # make message persistent
        ))
    connection.close()
    return " [x] Sent: %s" % message


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')