package com.wroom.searchservice.consumer.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserMessage {

    private String recipient;
    private String subject;
    private String text;
    private String test;

    public UserMessage() {}

    public UserMessage(@JsonProperty("recipient") String recipient,
                       @JsonProperty("subject") String subject,
                       @JsonProperty("text") String text,
                       @JsonProperty("test") String test) {
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
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
