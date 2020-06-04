package wroom.authservice.producer.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MailMessage {

    private String recipient;
    private String subject;
    private String text;

    public MailMessage() {}

    public MailMessage(@JsonProperty("recipient") String recipient,
                       @JsonProperty("subject") String subject,
                       @JsonProperty("text") String text) {
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
