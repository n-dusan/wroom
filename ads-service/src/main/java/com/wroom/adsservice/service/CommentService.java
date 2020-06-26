package com.wroom.adsservice.service;

import java.util.ArrayList;
import java.util.List;

import com.wroom.adsservice.exception.GeneralException;
import org.springframework.stereotype.Service;

import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.repository.CommentRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final AdService adService;
	
	public CommentService(CommentRepository commentRepository, AdService adService) {
		super();
		this.commentRepository = commentRepository;
		this.adService = adService;
	}

	public Comment findById(Long id) {
		return commentRepository.findById(id)
				.orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " comment"));
	}

	public Comment findByLocalId(Long id) {
		return commentRepository.findByLocalId(id);
	}
	
	/**
	 * Returns all comments that are approved and NOT deleted for a given ad.
	 * @param adId
	 * @return
	 */
	public List<Comment> findByAd(Long adId) {
		List<Comment> ret = this.commentRepository.findByAd(this.adService.findById(adId));
		List<Comment> list = new ArrayList<Comment>();
		for(Comment c: ret) {
			if(c.isApproved() == true) {
				list.add(c);
			}
		}
		return list;
	}

	public List<Comment> getVehicleComments(Long vehicleId) {
		return this.commentRepository.findCommentsByVehicle(vehicleId);
	}

	public Double getAverage(Long vehicleId) {
		return this.commentRepository.findAvgRating(vehicleId);
	}


	@Transactional
	public List<Comment> findCommentsByOwnerEmail(String email) {
		return this.commentRepository.findCommentsByOwnerEmail(email);
	}

	public Comment updateLocalId(Long commentId, Long localId) {
		Comment commentForUpdate = findById(commentId);

		commentForUpdate.setLocalId(localId);

		return this.commentRepository.save(commentForUpdate);

	}
	
}
