package rabbitmail.rabbitmail.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import rabbitmail.rabbitmail.conf.RabbitMQConfiguration;

@Service
public class MailCredentialReceiver {


    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void listen(String message) {
        System.out.println(message);
    }
}
