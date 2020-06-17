package xwsagent.wroomagent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.repository.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final AdService adService;
	
	public CommentService(CommentRepository commentRepository, AdService adService) {
		super();
		this.commentRepository = commentRepository;
		this.adService = adService;
	}


	public List<Comment> findByAd(Long adId) {
		return this.commentRepository.findByAd(this.adService.findById(adId));
	}
	
}
