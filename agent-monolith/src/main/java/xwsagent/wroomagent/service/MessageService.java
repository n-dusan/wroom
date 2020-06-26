package xwsagent.wroomagent.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.MessageConverter;
import xwsagent.wroomagent.domain.Message;
import xwsagent.wroomagent.domain.dto.MessageDTO;
import xwsagent.wroomagent.repository.MessageRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.clients.MessagesClient;

@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final MessagesClient messagesClient;
	private final UserRepository userRepository;
	
	public MessageService(MessageRepository messageRepository, MessagesClient messagesClient,
			UserRepository userRepository) {
		super();
		this.messageRepository = messageRepository;
		this.messagesClient = messagesClient;
		this.userRepository = userRepository;
	}

	public Message send(MessageDTO dto, String senderUsername, Long senderId) {
		Message entity = MessageConverter.toEntity(dto);
		entity.setRentRequestId(dto.getRentRequestId());
		entity.setFromUserId(senderId);
		entity.setFromUser(senderUsername);
		entity.setDate(new Date());
		
		Message saved = this.messageRepository.save(entity);
		
		this.messagesClient.send(saved);
		
		return saved;
	}
	
	public List<Message> getReceived(String username) {
		return this.messageRepository.findByToUser(username);
	}
	
	public List<Message> getSent(String username) {
		return this.messageRepository.findByFromUser(username);
	}
	
}
