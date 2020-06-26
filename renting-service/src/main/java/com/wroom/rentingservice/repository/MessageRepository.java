package com.wroom.rentingservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wroom.rentingservice.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByToUserId(Long id);
	List<Message> findByFromUserId(Long id);
	List<Message> findByToUser(String toUser);
	List<Message> findByFromUser(String fromUser);
	
//	/** used in syncs.
//	* */
//	@Transactional
//	@Query(value = "select m.* from message m inner join rent_request r on m.rent_request_id = r.id where r.owner_username = ?1", nativeQuery=true)
//	List<Message> findMessagesByOwnerEmail(String email);

	@Transactional
	@Query(value="select m.* from message m inner join rent_request r on m.rent_request_id = r.id " + 
			"inner join ad a on r.ad_id = a.id " + 
			"inner join vehicle v on a.vehicle_id = v.id " + 
			"where v.owner_username = ?1", nativeQuery=true)
	List<Message> findMessagesByOwnerEmail(String email);
}
