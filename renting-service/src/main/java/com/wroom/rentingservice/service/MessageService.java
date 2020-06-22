package com.wroom.rentingservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wroom.rentingservice.converter.MessageConverter;
import com.wroom.rentingservice.domain.Message;
import com.wroom.rentingservice.domain.dto.MessageDTO;
import com.wroom.rentingservice.exception.GeneralException;
import com.wroom.rentingservice.repository.MessageRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepository;

	public MessageService(MessageRepository messageRepository) {
		super();
		this.messageRepository = messageRepository;
	}

	public Message findById(Long id) {
		return messageRepository.findById(id)
				.orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " comment"));
	}
	
	public Message send(MessageDTO dto, String username, Long senderId) {
		Message entity = MessageConverter.toEntity(dto);
		entity.setRentRequestId(dto.getRentRequestId());
		entity.setFromUser(username);
		entity.setToUser(dto.getToUser());
		entity.setFromUserId(senderId);
		entity.setDate(new Date());
		return this.messageRepository.save(entity);
	}

	public List<Message> getReceived(Long userId, String username) {
//		List<Message> received = this.messageRepository.findByToUserId(userId);
		List<Message> received = this.messageRepository.findByToUser(username);
//		received.addAll(receivedByUsername);
		return received;
	}

	public List<Message> getSent(Long userId, String username) {
//		List<Message> sent = this.messageRepository.findByFromUserId(userId);
		List<Message> sent = this.messageRepository.findByFromUser(username);
//		sent.addAll(sentByUsername);
		return sent;
	}

	public Message updateLocalId(Long commentId, Long localId) {
		Message messageForUpdate = findById(commentId);
		messageForUpdate.setLocalId(localId);
		return this.messageRepository.save(messageForUpdate);

	}
	
	@Transactional
	public List<Message> findMessagesByOwnerEmail(String email) {
		return this.messageRepository.findMessagesByOwnerEmail(email);
	}

}
