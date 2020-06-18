package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.dto.CommentDTO;

public class CommentConverter extends AbstractConverter{

	public static CommentDTO fromEntity(Comment entity) {
		return new CommentDTO(
				entity.getId(),
				entity.getTitle(),
				entity.getContent(),
				entity.getCommentDate(),
				entity.getRate(),
				entity.getClientUsername(),
				entity.getClientId(),
				entity.getAd().getId(),
				entity.getReplyId() == null ? null : entity.getReplyId(),
				entity.isReply(),
				entity.isApproved(),
				entity.isDeleted()
		);
	}
	
	public static Comment toEntity(CommentDTO dto) {
		return new Comment(
				dto.getId(),
				dto.getTitle(),
				dto.getContent(),
				dto.getDate(),
				dto.isApproved(),
				dto.isDeleted(),
				dto.getRate(),
				dto.getUsername(),
				dto.getClientId(),
				null,
				dto.getReplyId(),
				dto.isReply()
		);
	}
	
}
