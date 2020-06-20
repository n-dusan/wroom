package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.soap.xsd.CommentSoap;

public class CommentSoapConverter {
	public static CommentSoap toSoapRequest(Comment entity) {
		CommentSoap ret = new CommentSoap();
		ret.setId(entity.getId());
		ret.setTitle(entity.getTitle());
		ret.setContent(entity.getContent());
		ret.setCommentDate(entity.getCommentDate());
		ret.setRequestedUserUsername(entity.getClientUsername());
		ret.setClientId(entity.getClientId());
		ret.setAd(entity.getAd().getId());
		ret.setReplyId(entity.getReplyId());
		ret.setApproved(entity.isApproved());
		ret.setDeleted(entity.isDeleted());
	
		return ret;
	}
	
	public static Comment fromSoapRequest(CommentSoap soap) {
		return new Comment(
				null,
				soap.getTitle(),
				soap.getContent(),
				soap.getCommentDate(),
				soap.isDeleted(),
				soap.isApproved(),
				soap.isRate(),
				soap.getRequestedUserUsername(),
				soap.getClientId(),
				null,
				soap.getReplyId(),
				soap.isReply()
		);
	}
}
