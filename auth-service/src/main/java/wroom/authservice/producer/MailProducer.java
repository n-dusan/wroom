package wroom.authservice.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import wroom.authservice.config.RabbitMQConfig;
import wroom.authservice.producer.messages.MailMessage;

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

    /*
     * Used for confirming E-Mail provided during sign-up proccess.
     */
    public void sendConfirmationMail(String email, String token) {
        log.info("Sending a message>>> ... ");
        MailMessage mailMessage = new MailMessage(email, 
        		"E-Mail Confirmation", 
        		"Hello, \n\n Please confirm your E-Mail Adress by clicking to following link:"
        		+ "\n https://localhost:4200/#/confirm/" + token
        		+ "\n\nKind regards,\nMonolith");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }
}
