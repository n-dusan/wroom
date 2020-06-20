package com.wroom.adsservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.repository.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final AdService adService;
	
	public CommentService(CommentRepository commentRepository, AdService adService) {
		super();
		this.commentRepository = commentRepository;
		this.adService = adService;
	}

	/**
	 * Returns all comments that are approved and NOT deleted for a given ad.
	 * @param adId
	 * @return
	 */
	public List<Comment> findByAd(Long adId) {
		List<Comment> ret = this.commentRepository.findByAd(this.adService.findById(adId));
		for(Comment c: ret) {
			if(c.isDeleted() || !c.isApproved()) {
				ret.remove(c);
			}
		}
		return ret;
	}

	public List<Comment> getVehicleComments(Long vehicleId) {
		return this.commentRepository.findCommentsByVehicle(vehicleId);
	}

	public Double getAverage(Long vehicleId) {
		return this.commentRepository.findAvgRating(vehicleId);
	}
	
}
