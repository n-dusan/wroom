package wroom.authservice.producer;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wroom.authservice.config.RabbitMQConfig;
import wroom.authservice.producer.messages.MailMessage;

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
     * Used for confirming E-Mail provided during sign-up proccess.
     */
    public void sendConfirmationMail(String email, String token) {
        log.info("Sending a message>>> ... ");
        MailMessage mailMessage = new MailMessage(email, 
        		"E-Mail Confirmation", 
        		"Hello, \n\n Please confirm your E-Mail Adress by clicking to following link:"
        		+ "\n https://localhost:4204/#/confirm/" + token
        		+ "\n\nKind regards,\nMonolith");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }
    
    public void sendForgotPasswordEmail(String email, String guid) {
    	log.info("Sending a message>>> ... ");
    	MailMessage mailMessage = new MailMessage(email, 
        		"Password Reset", 
        		"Hello, \n\n You can change your password following this link:"
        		+ "\n https://localhost:4204/#/reset-password/" + guid
        		+ "\n\nKind regards,\nMonolith");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }

    public void registerCompanyEmail(String email) {
        log.info("Sending a message>>> ... ");
        MailMessage mailMessage = new MailMessage(email,
                "Registration",
                "Greetings yung tryhard company. \n\n You are among the lucky ones to be part of this big great COVID19 family."
                        + "\nPlease, rent more cars and sync your database with us ASAP."
                        + "\nOur APIs are: \nfor vehicle = http://localhost:8073/ws\n" +
                        "for renting = http://localhost:8075/ws\n" +
                        "for ads = http://localhost:8074/ws\n"
                        + "\n\nKind regards,\nWroom");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }
}
