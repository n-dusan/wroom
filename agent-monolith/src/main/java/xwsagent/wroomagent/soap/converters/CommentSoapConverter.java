package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.converter.AbstractConverter;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.soap.xsd.CommentSoap;

public class CommentSoapConverter extends AbstractConverter {

	public static CommentSoap toSoapRequest(Comment entity) {
		CommentSoap ret = new CommentSoap();
		ret.setId(entity.getId());
		ret.setTitle(entity.getTitle());
		ret.setContent(entity.getContent());
		ret.setCommentDate(entity.getCommentDate());
		ret.setClientId(entity.getClientId());
		ret.setClientUsername(entity.getClientUsername());
		ret.setDeleted(entity.isDeleted());
		ret.setApproved(entity.isApproved());
		ret.setReply(true);
		ret.setReplyId(entity.getReplyId() == null ? null : entity.getReplyId());
		ret.setAdId(entity.getAd().getId());
		return ret;
	}

	public static Comment fromSoapRequest(CommentSoap soap) {
		return new Comment(
				soap.getLocalId(),
				soap.getTitle(),
				soap.getContent(),
				soap.getCommentDate(),
				soap.isDeleted(),
				soap.isApproved(),
				soap.getRate(),
				soap.getClientUsername(),
				soap.getClientId(),
				null,
				soap.getReplyId() == null ? null : new Long(soap.getReplyId()),
				soap.isReply()
		);
	}
}
