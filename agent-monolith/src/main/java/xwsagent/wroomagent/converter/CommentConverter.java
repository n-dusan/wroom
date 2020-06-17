package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.dto.CommentDTO;

public class CommentConverter extends AbstractConverter{

	public static CommentDTO fromEntity(Comment entity) {
		return new CommentDTO(
				entity.getId(),
				entity.getTitle(),
				entity.getContent(),
				entity.getDate(),
				entity.getRate(),
				entity.getClient().getEmail(),
				entity.getAd().getId(),
				entity.getReplyId() == null ? null : entity.getReplyId(),
				entity.isReply()
		);
	}
	
	public static Comment toEntity(CommentDTO dto) {
		return new Comment(
				dto.getId(),
				dto.getTitle(),
				dto.getContent(),
				dto.getDate(),
				null,
				null,
				dto.getRate(),
				null,
				null,
				dto.getReplyId(),
				dto.isReply()
		);
	}
	
}
