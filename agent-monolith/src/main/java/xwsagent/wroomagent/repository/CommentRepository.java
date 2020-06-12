package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	
}
