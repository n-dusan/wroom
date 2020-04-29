package rabbitmail.rabbitmail.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rabbitmail.rabbitmail.conf.RabbitMQConfiguration;
import rabbitmail.rabbitmail.domain.MailMessage;
import rabbitmail.rabbitmail.worker.Sender;

@Service
public class Listener {

    private static final Logger log = LoggerFactory.getLogger(Listener.class);

    @Autowired
    private Sender mailSender;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void listen(MailMessage message) {
        log.info("Received message > "+ message.getText());

        mailSender.send(message.getRecipient(), message.getSubject(), message.getText());
        log.info("Mail successfully sent.");
    }
}
