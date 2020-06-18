package xwsagent.wroomagent.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.MessageConverter;
import xwsagent.wroomagent.domain.Message;
import xwsagent.wroomagent.domain.dto.MessageDTO;
import xwsagent.wroomagent.repository.MessageRepository;
import xwsagent.wroomagent.soap.clients.MessagesClient;

@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final MessagesClient messagesClient;
	
	
	public MessageService(MessageRepository messageRepository, MessagesClient messagesClient) {
		super();
		this.messageRepository = messageRepository;
		this.messagesClient = messagesClient;
	}

	public Message send(MessageDTO dto, String senderUsername, Long senderId) {
		Message entity = MessageConverter.toEntity(dto);
		entity.setFromUserId(senderId);
		entity.setFromUser(senderUsername);
		entity.setDate(new Date());
		
		this.messagesClient.send(entity);
		
		return this.messageRepository.save(entity);
	}
	
	public List<Message> getReceived(Long userId) {
		return this.messageRepository.findByToUserId(userId);
	}
	
	public List<Message> getSent(Long userId) {
		return this.messageRepository.findByFromUserId(userId);
	}
	
}
