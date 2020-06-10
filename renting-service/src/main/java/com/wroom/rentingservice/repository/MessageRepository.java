package com.wroom.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
