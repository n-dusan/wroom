package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.domain.dto.CommentDTO;

public class CommentConverter extends AbstractConverter{
	public static CommentDTO fromEntity(Comment entity) {
        return new CommentDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCommentDate(),
                entity.isDeleted(),
                entity.isApproved(),
                entity.getClientUsername(),
                entity.getAd().getId()
        );
    }

    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setTitle(dto.getTitle());
        comment.setContent(dto.getContent());
        comment.setCommentDate(dto.getCommentDate());
        if(dto.isDeleted()) {
            comment.setDeleted(dto.isDeleted());
            comment.setDeleted(true);
        } else {
            comment.setDeleted(false);
        }
        if(dto.isApproved()) {
            comment.setApproved(dto.isApproved());
            comment.setApproved(true);
        } else {
            comment.setApproved(false);
        }
        
        comment.setClientUsername(dto.getClientUsername());
        return comment;
    }
}
