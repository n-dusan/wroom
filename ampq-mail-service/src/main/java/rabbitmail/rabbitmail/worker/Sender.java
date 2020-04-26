package rabbitmail.rabbitmail.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String sendTo, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendTo);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }
}
