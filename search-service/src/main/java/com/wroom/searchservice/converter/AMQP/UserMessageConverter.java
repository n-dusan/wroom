package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.FeatureMessage;
import com.wroom.searchservice.consumer.message.UserMessage;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.domain.dto.UserDTO;

public class UserMessageConverter {

    public static UserDTO fromMessage(UserMessage message) {
        UserDTO dto = new UserDTO();
        dto.setId(message.getId());
        dto.setName(message.getName());
        dto.setEmail(message.getEmail());
        dto.setEnabled(message.getEnabled());
        dto.setNonLocked(message.getNonLocked());
        dto.setSurname(message.getSurname());
        return dto;
    }
}
