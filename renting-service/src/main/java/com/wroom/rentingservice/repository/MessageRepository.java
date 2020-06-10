package com.wroom.rentingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByToUserId(Long id);
	List<Message> findByFromUserId(Long id);
	
}
