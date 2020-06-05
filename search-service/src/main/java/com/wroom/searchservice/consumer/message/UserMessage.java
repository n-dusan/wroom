package com.wroom.searchservice.consumer.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserMessage {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Boolean enabled;
    private Boolean nonLocked;

    private UserOperationEnum operation;

    public UserMessage() {}

    public UserMessage(@JsonProperty("id") Long id,
                       @JsonProperty("name") String name,
                       @JsonProperty("surname") String surname,
                       @JsonProperty("email") String email,
                       @JsonProperty("enabled") Boolean enabled,
                       @JsonProperty("nonLocked") Boolean nonLocked,
                       @JsonProperty("operation") UserOperationEnum operation) {
        this.id = id;
        this.name= name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
        this.nonLocked = nonLocked;
        this.operation = operation;
    }

    public UserOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(UserOperationEnum operation) {
        this.operation = operation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        this.nonLocked = nonLocked;
    }
}