package com.wroom.rentingservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.rentingservice.converter.MessageConverter;
import com.wroom.rentingservice.domain.Message;
import com.wroom.rentingservice.domain.dto.MessageDTO;
import com.wroom.rentingservice.repository.MessageRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		super();
		this.messageRepository = messageRepository;
	}

	public Message send(MessageDTO dto, Long senderId) {
		Message entity = MessageConverter.toEntity(dto);
		entity.setFromUserId(senderId);
		entity.setDate(new Date());
		return this.messageRepository.save(entity);
	}
	
	public List<Message> getReceived(Long userId, String username) {
		List<Message> received = this.messageRepository.findByToUserId(userId);
		List<Message> receivedByUsername  = this.messageRepository.findByToUser(username);
		received.addAll(receivedByUsername);
		return received;
	}
	
	public List<Message> getSent(Long userId, String username) {
		List<Message> sent = this.messageRepository.findByFromUserId(userId);
		List<Message> sentByUsername = this.messageRepository.findByFromUser(username);
		sent.addAll(sentByUsername);
		return sent;
	}
	
}
