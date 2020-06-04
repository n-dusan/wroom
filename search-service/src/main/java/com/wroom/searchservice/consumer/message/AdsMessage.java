package com.wroom.searchservice.consumer.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class AdsMessage {

    private String recipient;
    private String subject;
    private String text;

    public AdsMessage() {}

    @JsonCreator
    public AdsMessage(@JsonProperty("recipient") String recipient,
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