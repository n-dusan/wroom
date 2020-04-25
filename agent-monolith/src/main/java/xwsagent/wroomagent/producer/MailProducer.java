package xwsagent.wroomagent.producer;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class MailProducer {

    private RabbitTemplate rabbitTemplate;

    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String log) {
        rabbitTemplate.convertAndSend("mail", log.toString());
    }
}
