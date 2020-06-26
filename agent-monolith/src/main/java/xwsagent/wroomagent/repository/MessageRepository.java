package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xwsagent.wroomagent.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findByToUser(String toUser);
	List<Message> findByFromUser(String fromUser);
	
}
