package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByAd(Ad ad);
	
}
