package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Message;
import xwsagent.wroomagent.domain.dto.MessageDTO;

public class MessageConverter extends AbstractConverter {

	public static MessageDTO fromEntity(Message entity) {
        return new MessageDTO(
                entity.getToUserId(),
                entity.getRentRequestId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getFromUserId() == null ? null : entity.getFromUserId(),
                entity.getDate(),
                entity.getFromUser() == null ? null : entity.getFromUser(),
                entity.getToUser() == null ? null : entity.getToUser()
        );
    }

    public static Message toEntity(MessageDTO dto) {
    	return new Message(
    			null, 
    			null, 
    			dto.getToUserId() == null ? null : dto.getToUserId(), 
    			dto.getRentRequestId() == null ? null : dto.getRentRequestId(), 
    			dto.getTitle(), 
    			dto.getContent(),
    			dto.getDate(),
    			dto.getFromUser() == null ? null : dto.getFromUser(),
    			dto.getToUser() == null ? null : dto.getToUser());
    }
	
}
