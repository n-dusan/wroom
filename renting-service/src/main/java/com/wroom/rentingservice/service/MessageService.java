package com.wroom.rentingservice.service;

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
		return this.messageRepository.save(entity);
	}
	
}
