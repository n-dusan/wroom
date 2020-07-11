package wroom.authservice.converter;

import wroom.authservice.dto.UserDTO;
import wroom.authservice.producer.messages.UserMessage;
import wroom.authservice.producer.messages.UserOperationEnum;

public class AMQPUserConverter {

    public static UserMessage toFeatureMessage(UserDTO dto, UserOperationEnum operationEnum) {
        UserMessage message = new UserMessage();

        message.setSurname(dto.getSurname());
        message.setNonLocked(dto.getNonLocked());
        message.setName(dto.getName());
        message.setEnabled(dto.getEnabled());
        message.setEmail(dto.getEmail());
        message.setId(dto.getId());
        message.setOperation(operationEnum);

        return message;
    }
}
