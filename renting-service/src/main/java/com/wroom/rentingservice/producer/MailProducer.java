package com.wroom.rentingservice.producer;

import com.wroom.rentingservice.config.RabbitMQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MailProducer {

    @Autowired
    @Qualifier("rabbitTemplateEmail")
    private RabbitTemplate rabbitTemplate;

    public void send() {
        log.info("Sending a message>>> ... ");
        MailMessage mailMessage = new MailMessage("testboy@maildrop.cc,", "Hello!", "Yes, my child.");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }

    /*
     * New message email
     */
    public void sendNewMessageEmail(String email, String fromUser, String content) {
        log.info("Sending a new email message>>> ... ");
        MailMessage mailMessage = new MailMessage(email,
                "New message - " + fromUser,
                "Salutations my dear customer! \n\n You have a new message in your inbox:"
                        + "\n " + content
                        + "\nPlease be so kind to respond to that person, you buffoon."
                        + "\n\nKind regards,\nWroom");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }

}
