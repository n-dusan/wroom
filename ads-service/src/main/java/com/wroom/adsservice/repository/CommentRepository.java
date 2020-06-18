package com.wroom.adsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByAd(Ad ad);
	
}
