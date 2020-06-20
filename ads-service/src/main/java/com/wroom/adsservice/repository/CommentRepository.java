package com.wroom.adsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Comment;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByAd(Ad ad);

	@Query(value = "select c.* from comment c inner join ad a on c.ad_id = a.id where a.vehicle_id = ?1", nativeQuery=true)
	List<Comment> findCommentsByVehicle(Long vehicleId);

	@Query(value = "select avg(c.rate) from comment c inner join ad a on c.ad_id = a.id where a.vehicle_id = ?1", nativeQuery=true)
	Double findAvgRating(Long vehicleId);
}
