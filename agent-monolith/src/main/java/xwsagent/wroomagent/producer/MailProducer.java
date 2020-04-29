package xwsagent.wroomagent.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import xwsagent.wroomagent.config.RabbitMQConfig;

@Service
public class MailProducer {

    private static final Logger log = LoggerFactory.getLogger(MailProducer.class);

    private RabbitTemplate rabbitTemplate;

    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        log.info("Sending a message>>> ... ");
        MailMessage mailMessage = new MailMessage("testboy@maildrop.cc,", "Hello!", "Yes, my child.");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }
}
